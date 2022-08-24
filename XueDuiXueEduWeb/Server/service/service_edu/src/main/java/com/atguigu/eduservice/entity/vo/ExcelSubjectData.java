package com.atguigu.eduservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectData {
  @ExcelProperty(index = 0)
  private String oneSubjectName;

  @ExcelProperty(index = 1)
  private String twoSubjectName;
}
