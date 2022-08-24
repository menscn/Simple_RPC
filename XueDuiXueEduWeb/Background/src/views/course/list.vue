<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{
          (page - 1) * limit + scope.$index + 1
        }}</template>
      </el-table-column>

      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px" />
            </div>
            <div class="title">
              <a href>{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{
          scope.row.gmtCreate.substr(0, 10)
        }}</template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">{{
          scope.row.gmtModified.substr(0, 10)
        }}</template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{
            Number(scope.row.price) === 0
              ? "免费"
              : "¥" + scope.row.price.toFixed(2)
          }}
        </template>
      </el-table-column>
      <el-table-column
        prop="buyCount"
        label="付费学员"
        width="100"
        align="center"
      >
        <template slot-scope="scope">{{ scope.row.buyCount }}人</template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/add/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程信息</el-button
            >
          </router-link>
          <router-link :to="'/course/chapter/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程大纲</el-button
            >
          </router-link>
          <el-button
            type="text"
            size="mini"
            @click="delCourse(scope.row.id)"
            icon="el-icon-delete"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import course from "@/api/course";

export default {
  data() {
    return {
      listLoading: true, //是否显示loading信息
      list: [], //课程数据列表
      page: 1, //页码
      limit: 10, //每页记录数
      total: 0, //总记录数
    };
  },
  created() {
    this.getCourseList();
  },
  methods: {
    getCourseList() {
      course.getCourseInfo().then((response) => {
        this.list = response.data.list;
        this.listLoading = false;
      });
    },
    delCourse(id) {
      this.$confirm("此操作将永久删除该课程信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          course.delCourseInfo(id).then((response) => {
            //删除成功
            console.log("删除成功");
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //刷新页码
            this.getCourseList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>