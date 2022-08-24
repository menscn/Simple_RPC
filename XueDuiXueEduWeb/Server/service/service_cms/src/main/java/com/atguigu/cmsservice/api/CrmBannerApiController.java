package com.atguigu.cmsservice.api;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 专门与前台进行对接
@Api(description = "banner管理")
@RestController
@CrossOrigin
@RequestMapping("/cmsservice/banner")
public class CrmBannerApiController {
  @Autowired private CrmBannerService bannerService;

  @ApiOperation(value = "查询所有banner信息")
  @GetMapping("getAllBanner")
  public R getAllBanner() {
    List<CrmBanner> bannerList = bannerService.getAllBanner();
    return R.ok().data("bannerList", bannerList);
  }
}
