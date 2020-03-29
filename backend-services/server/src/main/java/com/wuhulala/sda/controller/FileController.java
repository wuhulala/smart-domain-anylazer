package com.hundsun.gefrm.admin.controller;

import com.wuhulala.base.dto.resp.BaseResp;
import com.wuhulala.base.exception.BaseException;
import com.wuhulala.sda.dto.CommonFileDTO;
import com.wuhulala.sda.service.file.CommonFileService;
import com.wuhulala.sda.util.FastDfsUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private CommonFileService commonFileService;

    /**
     * 文件最大size 50M
     */
    @Value("${file.max.size:1048576 * 50}")
    private long fileMaxSize;

    /**
     * 每次上传的最大文件数量
     */
    @Value("${file.once.number:5}")
    private int maxFileNumber;

    private String[] acceptFileTypes = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt",
            ".pdf", ".png", ".jpeg", ".jpg", ".zip", ".rar", ".7z", ".json"};

    /**
     * 文件上传
     *
     */
    @RequestMapping(value = {"/file/upload"},
            method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseResp uploadExcel(@RequestParam(value = "file", required = true) MultipartFile srcFile) {
        BaseResp baseResp = new BaseResp();

        try {
            String originFileName = srcFile.getOriginalFilename();

            assert originFileName != null;
            checkAcceptFileType(originFileName);

            String path = uploadFile(srcFile, originFileName);

            String id = saveFileToDb(originFileName, path);

            fillResult(baseResp, id);

            logger.info("文件{} => 上传成功", originFileName);

        } catch (MyException e) {
            baseResp.setResultInfo(BaseException.CUSTOM_ERROR_CODE, e.getMessage());
            logger.error("文件上传出错：", e);
        } catch (Exception e) {
            baseResp.setResultInfo(BaseException.CUSTOM_ERROR_CODE, "文件上传失败");
            logger.error("文件上传出错：", e);
        }
        return baseResp;
    }

    private void checkAcceptFileType(String originFileName) {
        String suffix = originFileName.substring(originFileName.lastIndexOf("."));
        if (notAcceptType(suffix)) {
            throw new BaseException(BaseException.CUSTOM_ERROR_CODE, "不被允许的文件类型");
        }
    }

    private String saveFileToDb(String originFileName, String path) {
        CommonFileDTO dto = new CommonFileDTO();
        dto.setName(originFileName);
        dto.setPath(path);
        return commonFileService.saveFile(dto).getItem();
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = {"/file/download"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadFile(@RequestParam("id") String id,
                             HttpServletRequest servletRequest,
                             HttpServletResponse servletResponse)
            throws IOException, MyException {

        CommonFileDTO fileInfo = getFileById(id);

        byte[] fileBytes = getFileBytes(id, fileInfo);

        try (BufferedInputStream bos = new BufferedInputStream(new ByteArrayInputStream(fileBytes));
             ServletOutputStream sos = servletResponse.getOutputStream()) {
            setComonHeader(servletResponse);
            setFileNameHeader(servletRequest, servletResponse, fileInfo);
            copy(bos, sos);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            logger.error("下载文件失败>>>>>>", e);
            throw e;
        }
    }

    private void copy(BufferedInputStream bos, ServletOutputStream sos) throws IOException {
        // 1M
        byte[] b = new byte[1024 * 1024];
        int read = 0;
        // 每次写1M
        while ((read = bos.read(b)) != -1) {
            sos.write(b, 0, read);
        }
        sos.flush();
    }

    private void setComonHeader(HttpServletResponse servletResponse) {
        servletResponse.setHeader("Set-Cookie", "fileDownload=true; path=/");
        servletResponse.setContentType("application/OCTET-STREAM;charset=ISO-8859-1");
    }

    private byte[] getFileBytes(@RequestParam("id") String id, CommonFileDTO fileInfo) throws IOException, MyException {
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = FastDfsUtil.download(fileInfo.getPath());
        } catch (IOException | MyException e) {
            logger.error("下载文件#{} 失败 [from fdfs]>>>>>>", id, e);
            throw e;
        }
        return fileBytes;
    }

    private CommonFileDTO getFileById(@RequestParam("id") String id) {
        return commonFileService.findFileById(id);
    }

    private void setFileNameHeader(HttpServletRequest servletRequest, HttpServletResponse servletResponse, CommonFileDTO fileInfo) throws UnsupportedEncodingException {
        String fileName = URLDecoder.decode(fileInfo.getName(), "UTF-8");
        if ((servletRequest.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) || (servletRequest.getHeader("User-Agent").contains("Trident"))) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        servletResponse.setHeader("Content-Disposition", "attachment;filename="
                + fileName);
    }

    private boolean notAcceptType(String suffix) {
        if (suffix == null) {
            return false;
        }
        return !ArrayUtils.contains(acceptFileTypes, suffix.toLowerCase());
    }

    private String getTargetFileDir(@RequestParam(value = "bizCode", required = true) String bizCode, String date) {
        String fileDir = "";
        String relativePath = File.separator + bizCode + File.separator + date;

        if (fileDir.endsWith(File.separator)) {
            fileDir = fileDir.substring(0, fileDir.length() - 1) + relativePath;
        } else {
            fileDir = fileDir + relativePath;
        }
        return fileDir;
    }

    private String renameFile(String orgFileName, String bizCode, String fileNewName, String date) {
        String fileType = orgFileName.substring(orgFileName.lastIndexOf("."));
        if (StringUtils.isBlank(fileNewName)) {
            fileNewName = bizCode + "_" + date + "_" + UUID.randomUUID().toString() + fileType;
        }
        return fileNewName;
    }

    private String uploadFile(MultipartFile file, String targetFilePath) throws IOException, MyException {
        return FastDfsUtil.upload(file.getInputStream(), targetFilePath);
    }

    private void fillResult(BaseResp baseResp, String targetFilePath) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("id", targetFilePath);
        baseResp.setMap(map);
    }


}
