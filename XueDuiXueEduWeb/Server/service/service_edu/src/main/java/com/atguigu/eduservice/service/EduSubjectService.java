package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目 服务类
 *
 * @author mSc
 * @since 2021-10-30
 */
public interface EduSubjectService extends IService<EduSubject> {

  void addSubject(MultipartFile file, EduSubjectService subjectService);

  List<OneSubjectVo> getAllSubject();
}
