package com.atguigu.smsservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.smsservice.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {
  // 发送短信
  @Override
  public boolean sendSmsPhone(String phone, Map<String, String> param) {
    if (StringUtils.isEmpty(phone)) return false;

    DefaultProfile profile =
        DefaultProfile.getProfile("default", "LTAI3buexRAagkdy", "A6hpWJbF3Zz6wj3jxuBe40Mwryt1Zz");
    IAcsClient client = new DefaultAcsClient(profile);

    CommonRequest request = new CommonRequest();
    // request.setProtocol(ProtocolType.HTTPS);
    request.setMethod(MethodType.POST);
    request.setDomain("dysmsapi.aliyuncs.com");
    request.setVersion("2017-05-25");
    request.setAction("SendSms");

    request.putQueryParameter("PhoneNumbers", phone);
    request.putQueryParameter("SignName", "我的谷粒在线教育网站"); // 审核通过签名
    request.putQueryParameter("TemplateCode", "SMS_183195440"); // 审核通过模板
    request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

    try {
      CommonResponse response = client.getCommonResponse(request);
      System.out.println(response.getData());
      return response.getHttpResponse().isSuccess();
    } catch (ClientException e) {
      e.printStackTrace();
    }
    return false;
  }
}
