package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程 服务实现类
 *
 * @author mSc
 * @since 2021-11-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
    implements EduCourseService {
  @Autowired private EduCourseDescriptionService descriptionService;

  @Autowired private EduChapterService chapterService;

  @Autowired private EduVideoService videoService;

  @Autowired private VodClient vodClient;

  // 添加课程信息
  @Override
  public String addCourseinfo(CourseInfoForm courseInfoForm) {
    //    先理清思路需求再写，列出步骤
    // 1添加课程信息
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfoForm, eduCourse);
    int insert = baseMapper.insert(eduCourse);
    if (insert == 0) {
      throw new GuliException(20001, "创建课程失败");
    }
    // 2获取课程id
    String courseId = eduCourse.getId();
    // 3添加课程描述信息
    EduCourseDescription courseDescription = new EduCourseDescription();
    courseDescription.setId(courseId);
    courseDescription.setDescription(courseInfoForm.getDescription());
    descriptionService.save(courseDescription);
    return courseId;
  }

  @Override
  public CourseInfoForm getCourseInfoById(String id) {
    // 1根据id查询课程信息
    EduCourse eduCourse = baseMapper.selectById(id);
    // 2封装课程信息
    CourseInfoForm courseInfoForm = new CourseInfoForm();
    BeanUtils.copyProperties(eduCourse, courseInfoForm);
    // 3根据id查询课程描述信息
    EduCourseDescription courseDescription = descriptionService.getById(id);
    // 4封装课程描述
    courseInfoForm.setDescription(courseDescription.getDescription());
    return courseInfoForm;
  }

  @Override
  public void updateCourseInfo(CourseInfoForm courseInfoForm) {
    // 1复制课程数据
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfoForm, eduCourse);
    // 2更新课程数据
    int update = baseMapper.updateById(eduCourse);
    // 3判断是否成功
    if (update == 0) {
      throw new GuliException(20001, "修改课程失败");
    }

    // 4更新课程描述
    EduCourseDescription courseDescription = new EduCourseDescription();
    courseDescription.setId(courseInfoForm.getId());
    courseDescription.setDescription(courseInfoForm.getDescription());
    descriptionService.updateById(courseDescription);
  }

  // 根据课程id查询课程发布信息
  @Override
  public CoursePublishVo getCoursePublishById(String id) {
    CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(id);
    return coursePublishVo;
  }

  @Override
  public void delCourseInfo(String id) {
    //    1. TODO 删除视频
    QueryWrapper<EduVideo> videoIdWapper = new QueryWrapper<>();
    videoIdWapper.eq("course_id", id);
    List<EduVideo> list = videoService.list(videoIdWapper);
    List<String> videoIdList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      // 获取集合中的EduVideo的对象
      EduVideo eduVideo = list.get(i);
      // 遍历各个视频的id，然后放入到list中，进行批量删除
      videoIdList.add(eduVideo.getVideoSourceId());
    }
    //    判断集合长度，若大于0则进行删除
    if (videoIdList.size() > 0) {
      vodClient.delVideoList(videoIdList);
    }
    //    2. 删除小节
    QueryWrapper<EduVideo> videoWapper = new QueryWrapper<>();
    videoWapper.eq("course_id", id);
    videoService.remove(videoWapper);
    //    3. 删除章节
    QueryWrapper<EduChapter> chapterWapper = new QueryWrapper<>();
    videoWapper.eq("course_id", id);
    chapterService.remove(chapterWapper);
    //    4. 删除课程描述
    descriptionService.removeById(id);
    //    5.删除课程
    int delete = baseMapper.deleteById(id);
    if (delete == 0) {
      throw new GuliException(20001, "删除失败");
    }
  }
}
