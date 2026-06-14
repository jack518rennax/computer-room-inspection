package com.patrol.framework.util;

import com.patrol.common.exception.BusinessException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * MinIO 工具类
 * <p>
 * 封装 MinioClient，提供 bucket / 上传 / 下载 / 删除 / URL 等常用操作。
 *
 * @author patrol-team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtil {

    private final MinioClient minioClient;

    /** 默认预签名 URL 过期时间：7 天 */
    private static final int DEFAULT_EXPIRY_SECONDS = (int) TimeUnit.DAYS.toSeconds(7);

    // ==================== Bucket 操作 ====================

    /**
     * 检查 Bucket 是否存在
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("检查 Bucket 失败: bucket={}", bucketName, e);
            throw new BusinessException("MinIO 操作失败: " + e.getMessage());
        }
    }

    /**
     * 创建 Bucket（如果不存在）
     */
    public void createBucket(String bucketName) {
        try {
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket 创建成功: {}", bucketName);
            } else {
                log.info("Bucket 已存在: {}", bucketName);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建 Bucket 失败: bucket={}", bucketName, e);
            throw new BusinessException("创建 Bucket 失败: " + e.getMessage());
        }
    }

    // ==================== 文件上传 ====================

    /**
     * 上传文件到指定 Bucket
     *
     * @param file       MultipartFile
     * @param bucketName Bucket 名称
     * @param objectName 对象名（存储路径+文件名）
     * @return 对象名
     */
    public String uploadFile(MultipartFile file, String bucketName, String objectName) {
        try {
            // 确保 Bucket 存在
            createBucket(bucketName);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            log.info("文件上传成功: bucket={}, object={}, size={}",
                    bucketName, objectName, file.getSize());
            return objectName;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("文件上传失败: bucket={}, object={}", bucketName, objectName, e);
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件到指定 Bucket（自动生成对象名）
     *
     * @param file       MultipartFile
     * @param bucketName Bucket 名称
     * @return 对象名
     */
    public String uploadFile(MultipartFile file, String bucketName) {
        String originalFilename = file.getOriginalFilename();
        String objectName = System.currentTimeMillis() + "_"
                + (originalFilename != null ? originalFilename : "unknown");
        return uploadFile(file, bucketName, objectName);
    }

    // ==================== 文件下载 ====================

    /**
     * 下载文件，返回输入流
     *
     * @param bucketName Bucket 名称
     * @param objectName 对象名
     * @return 文件输入流
     */
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("文件下载失败: bucket={}, object={}", bucketName, objectName, e);
            throw new BusinessException("文件下载失败: " + e.getMessage());
        }
    }

    // ==================== 文件删除 ====================

    /**
     * 删除文件
     *
     * @param bucketName Bucket 名称
     * @param objectName 对象名
     */
    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            log.info("文件删除成功: bucket={}, object={}", bucketName, objectName);
        } catch (Exception e) {
            log.error("文件删除失败: bucket={}, object={}", bucketName, objectName, e);
            throw new BusinessException("文件删除失败: " + e.getMessage());
        }
    }

    // ==================== URL 获取 ====================

    /**
     * 获取文件预签名访问 URL（默认 7 天有效）
     *
     * @param bucketName Bucket 名称
     * @param objectName 对象名
     * @return 预签名 URL
     */
    public String getFileUrl(String bucketName, String objectName) {
        return getFileUrl(bucketName, objectName, DEFAULT_EXPIRY_SECONDS);
    }

    /**
     * 获取文件预签名访问 URL
     *
     * @param bucketName    Bucket 名称
     * @param objectName    对象名
     * @param expirySeconds 过期时间（秒）
     * @return 预签名 URL
     */
    public String getFileUrl(String bucketName, String objectName, Integer expirySeconds) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expirySeconds)
                            .build());
        } catch (Exception e) {
            log.error("获取文件 URL 失败: bucket={}, object={}", bucketName, objectName, e);
            throw new BusinessException("获取文件 URL 失败: " + e.getMessage());
        }
    }
}
