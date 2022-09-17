package com.donggei.controller;


import com.donggei.domain.ResponseResult;
import com.donggei.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "文件上传")
    @ResponseBody
    @PostMapping(value = "/upload",consumes = "multipart/form-data",headers = "content-type=multipart/form-data")
    public ResponseResult uploadImg(@RequestPart MultipartFile img){
        return uploadService.uploadImg(img);
    }
//        @ApiImplicitParam(name = "img",value = "用户上传的文件",required = true,dataType = "content-type=multipart/form-data")
}