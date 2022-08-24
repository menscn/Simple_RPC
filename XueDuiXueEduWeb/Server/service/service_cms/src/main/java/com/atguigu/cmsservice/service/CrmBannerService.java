package com.atguigu.cmsservice.service;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 首页banner表 服务类
 *
 * @author mSc
 * @since 2021-11-12
 */
public interface CrmBannerService extends IService<CrmBanner> {

  List<CrmBanner> getAllBanner();
}
