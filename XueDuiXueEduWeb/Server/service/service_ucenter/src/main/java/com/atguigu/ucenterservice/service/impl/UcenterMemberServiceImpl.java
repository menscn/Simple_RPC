package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutils.utils.JwtUtils;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 会员表 服务实现类
 *
 * @author mSc
 * @since 2021-11-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember>
    implements UcenterMemberService {

  @Autowired private RedisTemplate<String, String> redisTemplate;

  // 用户注册
  @Override
  public void register(RegisterVo registerVo) {
    // 1 获取参数，验空
    //      不能为空的
    String nickname = registerVo.getNickname();
    String mobile = registerVo.getMobile();
    String code = registerVo.getCode();
    String password = registerVo.getPassword();
    if (StringUtils.isEmpty(nickname)
        || StringUtils.isEmpty(mobile)
        || StringUtils.isEmpty(code)
        || StringUtils.isEmpty(password)) {
      throw new GuliException(20001, "注册信息缺失");
    }
    // 2 验证手机号是否重复
    QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("mobile", mobile);
    Integer count = baseMapper.selectCount(queryWrapper);
    if (count > 0) {
      throw new GuliException(20001, "手机号重复");
    }
    // 3 验证短信验证码
    String redisCode = redisTemplate.opsForValue().get(mobile);
    if (!code.equals(redisCode)) {
      throw new GuliException(20001, "验证码错误");
    }

    // 4 使用MD5加密密码
    String md5Password = MD5.encrypt(password);
    // 5 补充信息后插入数据库
    UcenterMember ucenterMember = new UcenterMember();
    ucenterMember.setNickname(nickname);
    ucenterMember.setMobile(mobile);
    ucenterMember.setPassword(md5Password);
    ucenterMember.setAvatar(
        "https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
    ucenterMember.setIsDisabled(false);
    baseMapper.insert(ucenterMember);
  }

  // 用户登录
  @Override
  public String login(LoginVo loginVo) {
    // 1 获取参数，验空
    String mobile = loginVo.getMobile();
    String password = loginVo.getPassword();
    if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
      throw new GuliException(20001, "手机号或密码有误");
    }
    // 2 根据手机号获取用户信息
    QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("mobile", mobile);
    UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
    if (ucenterMember == null) {
      throw new GuliException(20001, "手机号或密码有误");
    }
    // 3 密码加密后验证密码
    String md5Password = MD5.encrypt(password);
    if (!md5Password.equals(ucenterMember.getPassword())) {
      throw new GuliException(20001, "手机号或密码有误");
    }
    // 4生成token字符串
    String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

    return token;
  }
}
