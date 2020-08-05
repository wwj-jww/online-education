<template>
  <div class="app-container">
    轮播图表单
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="轮播图名称" width="80" />

      <el-table-column prop="imageUrl" label="轮播图地址" />

      <el-table-column label="链接地址" width="80">
        <template slot-scope="scope">
          {{ scope.row.linkUrl==="/teacher"?'讲师模块':'课程模块' }}
        </template>
      </el-table-column>

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/banner/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

       <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>
<script>

//导入teacher.js文件
import banner from '@/api/edu/banner'

export default {
    //写核心代码位置
    data () {//定义数据变量和初始值
        return {
            list:null,//查询之后接口返回集合
            page:0,//当前页
            limit:5,//每页记录数
            total:0//总记录数
          
        }
    },

    created () {//调用methods定义方法
        this.getList()
    },

    methods: {//定义方法，调用teacher.js中的方法 
        getList(page=1){
            this.page=page
            banner.getBannerList(this.page,this.limit)
            .then(response => {
                this.list = response.data.records
                this.total = response.data.total
            })
        },
        //删除的方法
        removeDataById(id) {
            this.$confirm('此操作将永久删除banner, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {  //点击确定，执行then方法
                //调用删除的方法
                banner.deleteBanner(id)
                    .then(response =>{//删除成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //回到列表页面
                    this.getList()
                })
            }) //点击取消，执行catch方法
        }
    }
}
</script>