package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.ExcelSubjectData;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.entity.vo.TwoSubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目 服务实现类
 *
 * @author mSc
 * @since 2021-10-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject>
    implements EduSubjectService {

  @Override
  public void addSubject(MultipartFile file, EduSubjectService subjectService) {
    try {
      InputStream inputStream = file.getInputStream(); // 获得文件流
      EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService))
          .sheet()
          .doRead();

    } catch (IOException e) {
      e.printStackTrace();
      throw new GuliException(20001, "导入课程分类失败");
    }
  }

  //  查询所有课程分类

  // 查询所有课程分类
  @Override
  public List<OneSubjectVo> getAllSubject() {
    // 1查询所有一级分类
    QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
    wrapperOne.eq("parent_id", "0");
    List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
    // 2查询所有二级分类
    QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
    wrapperTwo.ne("parent_id", "0");
    List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
    // 3封装一级分类
    List<OneSubjectVo> allSubjectList = new ArrayList<>();
    for (int i = 0; i < oneSubjectList.size(); i++) {
      // 3.1取出每一个一级分类
      EduSubject oneSubject = oneSubjectList.get(i);
      // 3.2EduSubject转化OneSubjectVo
      OneSubjectVo oneSubjectVo = new OneSubjectVo();
      //            oneSubjectVo.setId(oneSubject.getId());
      //            oneSubjectVo.setTitle(oneSubject.getTitle());
      BeanUtils.copyProperties(oneSubject, oneSubjectVo);
      allSubjectList.add(oneSubjectVo);
      // 4找到根一级有关的二级进行封装
      List<TwoSubjectVo> twoSubjectVos = new ArrayList<>();
      for (int m = 0; m < twoSubjectList.size(); m++) {
        // 4.1取出每一个二级分类
        EduSubject twoSubject = twoSubjectList.get(m);
        // 4.2 判断是否归属此一级
        if (twoSubject.getParentId().equals(oneSubject.getId())) {
          // 4.3EduSubject转化TwoSubjectVo
          TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
          BeanUtils.copyProperties(twoSubject, twoSubjectVo);
          twoSubjectVos.add(twoSubjectVo);
        }
      }
      oneSubjectVo.setChildren(twoSubjectVos);
    }

    return allSubjectList;
  }
}
