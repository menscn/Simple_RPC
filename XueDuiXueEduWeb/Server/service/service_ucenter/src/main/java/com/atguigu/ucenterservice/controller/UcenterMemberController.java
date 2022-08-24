package com.atguigu.ucenterservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.utils.JwtUtils;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员表 前端控制器
 *
 * @author mSc
 * @since 2021-11-21
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

  @Autowired private UcenterMemberService memberService;

  @ApiOperation(value = "用户注册")
  @PostMapping("register")
  public R register(@RequestBody RegisterVo registerVo) {
    memberService.register(registerVo);
    return R.ok();
  }

  @ApiOperation(value = "用户登录")
  @PostMapping("login")
  public R login(@RequestBody LoginVo loginVo) {
    String token = memberService.login(loginVo);
    return R.ok().data("token", token);
  }

  // 通过token获取用户id，然后再结合这个id进入数据库去查询用户
  @ApiOperation(value = "根据token字符串获取用户信息")
  @GetMapping("getUcenterByToken")
  public R getUcenterByToken(HttpServletRequest request) {
    String memberId = JwtUtils.getMemberIdByJwtToken(request);
    UcenterMember ucenterMember = memberService.getById(memberId);
    return R.ok().data("ucenterMember", ucenterMember);
  }
}
