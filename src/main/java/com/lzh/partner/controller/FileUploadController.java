package com.lzh.partner.controller;

import com.lzh.partner.common.BaseResponse;
import com.lzh.partner.common.ErrorCode;
import com.lzh.partner.common.ResultUtils;
import com.lzh.partner.exception.BusinessException;
import com.lzh.partner.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Classname FileUploadController
 * @Description 上传文件接口
 * @Version 1.0.0
 * @Date 2023/11/18 1:59
 * @Created by lzh
 */

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"传入文件失败");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return ResultUtils.success(url);
    }

}
