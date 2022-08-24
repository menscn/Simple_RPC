package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员表 服务类
 *
 * @author mSc
 * @since 2021-11-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {

  void register(RegisterVo registerVo);

  String login(LoginVo loginVo);
}
