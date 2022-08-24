package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import com.atguigu.servicebase.handler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
  @Override
  public String uploadFileOSS(MultipartFile file) {

    String endpoint = ConstantPropertiesUtil.END_POINT;
    String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
    String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
    String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
    String fileName = file.getOriginalFilename();

    // 创建OSSClient实例
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    try {
      // 获取上传文件流
      InputStream inputStream = file.getInputStream();
      //      上传之前，在文件名之前添加UUID
      fileName = UUID.randomUUID().toString() + fileName;
      // 优化文件存储路径（/2021/03/09/uuid+01.jpg）
      String path = new DateTime().toString("yyyy/MM/dd");
      fileName = path + "/" + fileName;
      ossClient.putObject(bucketName, fileName, inputStream); // 通过客户端完成上传操作
      // 关闭OSSClient。
      ossClient.shutdown();
      // https://guli-file201021.oss-cn-beijing.aliyuncs.com/01.jpg
      //        拼接url字符串
      String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
      return url;

    } catch (IOException e) {
      e.printStackTrace();
      //      统一异常处理方式
      throw new GuliException(20001, "上传失败");
    }
  }
}
