package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程 服务类
 *
 * @author mSc
 * @since 2021-11-05
 */
@Service
public interface EduChapterService extends IService<EduChapter> {

  List<ChapterVo> getChapterVideoById(String courseId);
}
