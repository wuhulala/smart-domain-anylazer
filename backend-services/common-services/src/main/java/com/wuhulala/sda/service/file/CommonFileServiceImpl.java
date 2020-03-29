package com.wuhulala.sda.service.file;

import com.wuhulala.base.dto.resp.BaseResp;
import com.wuhulala.sda.dto.CommonFileDTO;
import com.wuhulala.sda.mapper.domain.CommonFileMapper;
import com.wuhulala.sda.model.CommonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommonFileServiceImpl implements CommonFileService {

    @Autowired
    private CommonFileMapper fileMapper;

    @Override
    public BaseResp<String> saveFile(CommonFileDTO dto) {
        CommonFile commonFile = new CommonFile();
        commonFile.setName(dto.getName());
        commonFile.setPath(dto.getPath());
        commonFile.setGmtCreate(new Date());
        fileMapper.insert(commonFile);
        return new BaseResp<>(commonFile.getId());
    }

    @Override
    public CommonFileDTO findFileById(String id) {
        CommonFile commonFile = fileMapper.selectById(id);
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        commonFileDTO.setName(commonFile.getName());
        commonFileDTO.setPath(commonFile.getPath());
        return commonFileDTO;
    }
}
