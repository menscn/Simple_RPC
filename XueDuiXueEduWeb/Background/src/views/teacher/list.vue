<template>
  <div class="app-container">
    讲师列表
    <br />
    <br />
    <!-- inline表示一行显示 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <!-- teacherQuery.name 与数据名保持一直 -->
        <el-input v-model="teacherQuery.name" placeholder="讲师名" />
      </el-form-item>

      <el-form-item>
        <el-select
          v-model="teacherQuery.level"
          clearable
          placeholder="讲师头衔"
        >
          <!-- el-option下拉表单 -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <!-- placeholder 提示信息 -->
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <!-- 按钮 -->
      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getTeacherPageQuery()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (current - 1) * limit + scope.$index + 1 }}
          <!-- //scope 当前区域 -->
        </template>
      </el-table-column>
      <!-- name 这些一定要与list中对应 -->
      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          <!-- scope.row.level 表示从当前区域的每一行数据中获取level数据 -->
          {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!-- 路由的跳转，进入到讲师添加页面 -->
          <router-link :to="'/teacher/add/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeConfirm(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="current"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getTeacherPageQuery"
    />
  </div>
</template>

<script>
import teacher from "@/api/teacher";

export default {
  data() {
    return {
      current: 1, //页码
      limit: 10, //每页多少行
      teacherQuery: {}, //查询条件
      list: [], //列表数据
      total: 0, //总数
    };
  },
  created() {
    this.getTeacherPageQuery();
  },
  methods: {
    // 这个方法的参数与this.current不同，current = 1表示默认指定，当没有传参数时，默认等于1
    getTeacherPageQuery(current = 1) {
      this.current = current;
      teacher
        .getTeacherPageVo(this.current, this.limit, this.teacherQuery)
        .then((response) => {
          console.log(response);
          this.list = response.data.list;
          this.total = response.data.total;
        });
    },
    // 清空
    resetData() {
      this.teacherQuery = {}; //将查询对象情况
      this.getTeacherPageQuery(); //调用方法获取当前信息，相当于重新查询数据库，获取信息然后控进行展示
    },
    // 删除讲师
    removeDataById(id) {
      teacher.delTeacher(id).then((response) => {
        console.log("删除成功");
        // 删除成功然后刷新表格
        this.getTeacherPageQuery();
      });
    },
    removeConfirm(id) {
      this.$confirm("此操作将永久删除该教师信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.removeDataById(id);
          this.$message({
            type: "success",
            message: "删除成功!",
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