package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 前端控制器
 *
 * @author mSc
 * @since 2021-11-01
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {
  @Autowired private EduCourseService courseService;

  /*添加课程信息
   * 前端通过post将对象封装成json格式传回来*/
  @ApiOperation(value = "添加课程信息")
  @PostMapping("addCourseinfo")
  // {"code":20000,"data":{"token":"admin"}}
  public R addCourseinfo(@RequestBody CourseInfoForm courseInfoForm) {
    String courseId = courseService.addCourseinfo(courseInfoForm);
    return R.ok().data("courseId", courseId);
  }

  @ApiOperation(value = "根据id课程信息")
  @GetMapping("getCourseInfoById/{id}")
  public R getCourseInfoById(@PathVariable String id) {
    CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
    return R.ok().data("courseInfo", courseInfoForm);
  }

  @ApiOperation(value = "修改课程信息")
  @PostMapping("updateCourseInfo")
  public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {

    courseService.updateCourseInfo(courseInfoForm);
    return R.ok();
  }

  @ApiOperation(value = "根据课程id查询课程发布信息")
  @GetMapping("getCoursePublishById/{id}")
  public R getCoursePublishById(@PathVariable String id) {
    CoursePublishVo coursePublishVo = courseService.getCoursePublishById(id);
    return R.ok().data("coursePublishVo", coursePublishVo);
  }

  //  修改数据库中的某一个状态
  @ApiOperation(value = "根据id发布课程")
  @PutMapping("publishCourse/{id}")
  public R publishCourse(@PathVariable String id) {
    EduCourse eduCourse = courseService.getById(id);
    eduCourse.setStatus("Normal");
    courseService.updateById(eduCourse);
    return R.ok();
  }

  @ApiOperation(value = "查询所有课程信息")
  @GetMapping("getCourseInfo")
  // TODO 实现带条件、带分页查询
  public R getCourseInfo() {
    List<EduCourse> list = courseService.list(null);
    return R.ok().data("list", list);
  }

  //  按照一定顺序进行删除
  @ApiOperation(value = "根据id删除课程相关信息")
  @DeleteMapping("delCourseInfo/{id}")
  public R delCourseInfo(@PathVariable String id) {
    courseService.delCourseInfo(id);
    return R.ok();
  }
}
