package com.atguigu.wxlogin.controller;

import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.wxlogin.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 会员表 前端控制器
 *
 * @author mSc
 * @since 2021-11-22
 */
@Api(description = "章节管理")
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class UcenterMemberController {

  // 主要根据官网文档，进行字符串拼接
  @GetMapping("login")
  public String genQrConnect(HttpSession session) {

    // 微信开放平台授权baseUrl
    String baseUrl =
        "https://open.weixin.qq.com/connect/qrconnect"
            + "?appid=%s"
            + "&redirect_uri=%s"
            + "&response_type=code"
            + "&scope=snsapi_login"
            + "&state=%s"
            + "#wechat_redirect";

    // 回调地址
    String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; // 获取业务服务器重定向地址
    try {
      redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); // url编码
    } catch (UnsupportedEncodingException e) {
      throw new GuliException(20001, e.getMessage());
    }

    // 防止csrf攻击（跨站请求伪造攻击）
    // String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
    String state = "mSc"; // 为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
    System.out.println("state = " + state);

    // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
    // 键："wechar-open-state-" + httpServletRequest.getSession().getId()
    // 值：satte
    // 过期时间：30分钟

    // 生成qrcodeUrl
    String qrcodeUrl =
        String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, state);

    return "redirect:" + qrcodeUrl;
  }
}
