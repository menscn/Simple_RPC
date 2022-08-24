package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 课程 Mapper 接口
 *
 * @author mSc
 * @since 2021-11-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

  CoursePublishVo getCoursePublishById(String id);
}
