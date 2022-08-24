package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 课程 服务类
 *
 * @author mSc
 * @since 2021-11-01
 */
public interface EduCourseService extends IService<EduCourse> {
  String addCourseinfo(CourseInfoForm courseInfoForm);

  CourseInfoForm getCourseInfoById(String id);

  void updateCourseInfo(CourseInfoForm courseInfoForm);

  CoursePublishVo getCoursePublishById(String id);

  void delCourseInfo(String id);
}
