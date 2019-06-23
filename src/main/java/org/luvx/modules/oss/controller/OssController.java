package org.luvx.modules.oss.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.luvx.modules.common.Response;
import org.luvx.modules.oss.entity.OssEntity;
import org.luvx.modules.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @ClassName: org.luvx.modules.oss.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/23 10:24
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    OssService service;

    @PostMapping("/upload")
    public R<OssEntity> upload(@RequestParam("file") MultipartFile file) {
        Objects.requireNonNull(file, "上传文件为空");
        OssEntity entity = OssEntity.builder().url(null).createDate(null).build();
        service.save(entity);
        return Response.ok(entity);
    }
}
