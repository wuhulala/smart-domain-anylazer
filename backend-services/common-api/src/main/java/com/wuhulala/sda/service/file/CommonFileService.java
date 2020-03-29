package com.wuhulala.sda.service.file;

import com.wuhulala.base.dto.resp.BaseResp;
import com.wuhulala.sda.dto.CommonFileDTO;

public interface CommonFileService {

    BaseResp<String> saveFile(CommonFileDTO dto);

    CommonFileDTO findFileById(String id);

}
