package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author mSc
 * @since 2021-10-10
 */
@RestController
@Api(description = "讲师管理-增删改查")
@RequestMapping("/eduservice/eduteacher")
@CrossOrigin
public class EduTeacherController {
  @Autowired private EduTeacherService teacherService;

  @ApiOperation(value = "获取所有讲师列表")
  @GetMapping
  public R getAllTeacher() {
    List<EduTeacher> list = teacherService.list(null);
    return R.ok().data("list", list);
  }

  @ApiOperation(value = "根据ID删除讲师")
  @DeleteMapping("{id}")
  public R removeById(
      @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
    boolean remove = teacherService.removeById(id);
    if (remove) {
      return R.ok();
    } else {
      return R.error();
    }
  }

  @ApiOperation(value = "分页查询讲师列表")
  @GetMapping("getTeacherPage/{page}/{limit}")
  public R pageList(
      @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit) {

    Page<EduTeacher> pageParam = new Page<>(page, limit);

    teacherService.page(pageParam, null);
    List<EduTeacher> records = pageParam.getRecords();
    long total = pageParam.getTotal(); // 总共多少条记录

    return R.ok().data("total", total).data("rows", records);
  }

  @ApiOperation(value = "带条件分页查询讲师列表")
  @PostMapping("getTeacherPage/{page}/{limit}")
  public R getPageList(
      @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
      @ApiParam(name = "teacherQuery", value = "查询对象", required = true) @RequestBody
          TeacherQuery teacherQuery) {
    //  获取查询条件
    String name = teacherQuery.getName();
    Integer level = teacherQuery.getLevel();
    String begin = teacherQuery.getBegin();
    String end = teacherQuery.getEnd();

    //        使用条件查询构造器

    QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

    //        判读那是否为空

    if (!StringUtils.isEmpty(name)) {
      wrapper.like("name", name); // 模糊查询
    }
    if (!StringUtils.isEmpty(level)) {
      wrapper.eq("level", level); // eq 严格匹配
    }
    if (!StringUtils.isEmpty(begin)) {
      wrapper.ge("gmt_create", begin); // ge 大于等于  gmt_create和数据库里对应
    }
    if (!StringUtils.isEmpty(end)) {
      wrapper.le("gmt_create", end); // le  小于等于
    }

    Page<EduTeacher> pageParam = new Page<>(page, limit);

    teacherService.page(pageParam, wrapper);
    List<EduTeacher> records = pageParam.getRecords();
    long total = pageParam.getTotal(); // 总共多少条记录

    return R.ok().data("total", total).data("list", records);
  }

  @ApiOperation(value = "新增讲师")
  @PostMapping("addTeacher")
  public R save(
      @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody
          EduTeacher teacher) {

    //        使用@RequestBody之后，前端传回的数据会自动封装成对象格式
    teacherService.save(teacher);
    return R.ok();
  }

  @ApiOperation(value = "根据ID查询讲师")
  @GetMapping("getTeacherById/{id}")
  public R getById(
      @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {

    EduTeacher teacher = teacherService.getById(id);
    return R.ok().data("item", teacher);
  }

  @ApiOperation(value = "修改讲师")
  @PostMapping("updateTeacher")
  public R updateTeacher(
      @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody
          EduTeacher teacher) {

    boolean update = teacherService.updateById(teacher);
    if (update) {
      return R.ok();
    } else {
      return R.error();
    }
  }
}
