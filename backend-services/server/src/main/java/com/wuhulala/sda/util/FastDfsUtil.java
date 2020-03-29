package com.wuhulala.sda.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FastDfs 类
 *
 * @author wuhulala
 */
public class FastDfsUtil {

    private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;

    static {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getTrackerServer();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException | MyException e) {
            throw new RuntimeException("FastDfs工具类初始化失败!");
        }
    }

    public static String upload(InputStream inputStream, String filename) throws IOException, MyException {
        String suffix = "";
        try {
            suffix = filename.substring(filename.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new RuntimeException("参数filename不正确!格式例如：a.png");
        }
        //FastDfs的存储路径
        StringBuilder savePath = new StringBuilder();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[4 * 1024 * 1024];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            swapStream.write(buff, 0, len);
        }
        byte[] in2b = swapStream.toByteArray();
        //上传文件
        String[] strings = storageClient.upload_file(in2b, suffix, null);
        for (String str : strings) {
            savePath.append("/").append(str);
        }
        return savePath.toString();
    }

    public static String upload(String filepath) throws IOException, MyException {
        String suffix = "";
        try {
            suffix = filepath.substring(filepath.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new RuntimeException("上传的不是文件!");
        }
        StringBuilder savePath = new StringBuilder();
        String[] strings = storageClient.upload_file(filepath, suffix, null);
        for (String str : strings) {
            savePath.append("/").append(str);
        }
        return savePath.toString();
    }


    public static boolean download(String remotePath, String localPath) throws IOException, MyException {
        String group = "";
        String path = "";
        try {
            //第二个"/"索引位置
            int secondIndex = remotePath.indexOf("/", 2);
            //类似：group1
            group = remotePath.substring(1, secondIndex);
            //类似：M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png
            path = remotePath.substring(secondIndex + 1);
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        int result = storageClient.download_file(group, path, localPath);
        if (result != 0) {
            throw new RuntimeException("下载文件失败：文件路径不对或者文件已删除!");
        }
        return true;
    }


    public static byte[] download(String savePath) throws IOException, MyException {
        byte[] bs = null;
        String group = "";
        String path = "";
        try {
            int secondIndex = savePath.indexOf("/", 2);
            group = savePath.substring(1, secondIndex);
            path = savePath.substring(secondIndex + 1);
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        bs = storageClient.download_file(group, path);
        return bs;
    }


    public static boolean delete(String remotePath) throws IOException, MyException {
        String group = "";
        String path = "";
        try {
            int secondIndex = remotePath.indexOf("/", 2);
            group = remotePath.substring(1, secondIndex);
            path = remotePath.substring(secondIndex + 1);
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        int result = storageClient.delete_file(group, path);
		//删除文件，0表示删除成功
        if (result != 0) {
            throw new RuntimeException("删除文件失败：文件路径不对或者文件已删除!");
        }
        return true;
    }


    public static FileInfo getFileInfo(String remotePath) throws IOException, MyException {
        String group = "";
		String path = "";
        try {
            int secondIndex = remotePath.indexOf("/", 2);
            group = remotePath.substring(1, secondIndex);
            path = remotePath.substring(secondIndex + 1);
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
		return storageClient.get_file_info(group, path);
    }

}