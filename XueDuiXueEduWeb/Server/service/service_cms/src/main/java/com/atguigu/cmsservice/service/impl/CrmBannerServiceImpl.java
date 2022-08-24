package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.mapper.CrmBannerMapper;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页banner表 服务实现类
 *
 * @author mSc
 * @since 2021-11-12
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
    implements CrmBannerService {

  @Cacheable(value = "banner", key = "'selectIndexList'")
  @Override
  public List<CrmBanner> getAllBanner() {
    System.out.println("进入service，查询数据库数据");
    List<CrmBanner> bannerList = baseMapper.selectList(null);
    return bannerList;
  }
}
