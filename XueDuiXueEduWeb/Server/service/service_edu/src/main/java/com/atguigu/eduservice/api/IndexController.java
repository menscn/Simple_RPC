package com.atguigu.eduservice.api;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "首页显示")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexController {
  //    课程
  @Autowired private EduCourseService courseService;
  //    讲师
  @Autowired private EduTeacherService teacherService;

  @ApiOperation(value = "首页展示8条课程信息与4条讲师信息")
  @GetMapping("getCourseTeacherList")
  public R getCourseTeacherList() {
    // 8条课程信息
    QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
    courseWrapper.orderByDesc("gmt_create");
    //    拼接在最后，实现分页功能
    courseWrapper.last("LIMIT 8");
    List<EduCourse> courseList = courseService.list(courseWrapper);

    //      4条讲师信息
    QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
    teacherWrapper.orderByDesc("gmt_create");
    //    拼接在最后，实现分页功能
    teacherWrapper.last("LIMIT 4");
    List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
    return R.ok().data("courseList", courseList).data("teacherList", teacherList);
  }
}
