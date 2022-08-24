package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 前端控制器
 *
 * @author mSc
 * @since 2021-11-05
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

  @Autowired private EduChapterService chapterService;

  @ApiOperation(value = "根据课程id查询章节、小节信息")
  @GetMapping("getChapterVideoById/{courseId}")
  public R getChapterVideoById(@PathVariable String courseId) {
    List<ChapterVo> chapterVoList = chapterService.getChapterVideoById(courseId);
    return R.ok().data("chapterVideoList", chapterVoList);
  }

  //  从前端获取数据进行存储
  @ApiOperation(value = "添加章节")
  @PostMapping("addChapter")
  public R addChapter(@RequestBody EduChapter eduChapter) {
    chapterService.save(eduChapter);
    return R.ok();
  }

  @ApiOperation(value = "根据id删除章节")
  @DeleteMapping("delChapter/{id}")
  public R delChapter(@PathVariable String id) {
    chapterService.removeById(id);
    return R.ok();
  }

  @ApiOperation(value = "根据id查询章节")
  @GetMapping("getChapterById/{id}")
  public R getChapterById(@PathVariable String id) {
    EduChapter eduChapter = chapterService.getById(id);
    return R.ok().data("eduChapter", eduChapter);
  }

  @ApiOperation(value = "修改章节")
  @PostMapping("updateChapter")
  public R updateChapter(@RequestBody EduChapter eduChapter) {
    chapterService.updateById(eduChapter);
    return R.ok();
  }
}
