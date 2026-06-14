package com.patrol.system.controller;

import com.patrol.common.R;
import com.patrol.framework.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 文件上传下载 Controller（MinIO 验证用）
 *
 * @author patrol-team
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final MinioUtil minioUtil;

    /** 默认 Bucket（来自 minio.bucket 配置） */
    @Value("${minio.bucket:patrol}")
    private String bucket;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error(400, "上传文件不能为空");
        }
        String objectName = minioUtil.uploadFile(file, bucket);
        String url = minioUtil.getFileUrl(bucket, objectName);
        log.info("文件上传成功: object={}, url={}", objectName, url);
        return R.ok("上传成功", objectName);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{objectName}")
    public void download(@PathVariable String objectName, HttpServletResponse response) {
        try (InputStream inputStream = minioUtil.downloadFile(bucket, objectName)) {
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode(objectName, "UTF-8").replace("+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

            try (OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            log.error("文件下载失败: object={}", objectName, e);
            response.setStatus(500);
        }
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{objectName}")
    public R<Void> delete(@PathVariable String objectName) {
        minioUtil.deleteFile(bucket, objectName);
        log.info("文件删除成功: object={}", objectName);
        return R.ok();
    }

    /**
     * 获取文件访问 URL
     */
    @GetMapping("/url/{objectName}")
    public R<String> getUrl(@PathVariable String objectName) {
        String url = minioUtil.getFileUrl(bucket, objectName);
        return R.ok(url);
    }
}
