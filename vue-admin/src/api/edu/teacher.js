import request from '@/utils/request'


export default{

    //讲师列表 (组合条件查询带分页)
    //current当前页，size每页显示条数，teacherQuery条件对象
    getTeacherList(current,size,teacherQuery){
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${size}`,
            method: 'post',
            //teacherQuery条件对象 后端使用RequestBody接收
            //用data将条件对象转换为json
            data: teacherQuery
          })
    },

    //删除讲师
    deleteTeacherById(id){
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete'
          })
    },

    //添加讲师
    addTeacher(teacher){
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
             //teacher为添加的讲师对象 后端使用RequestBody接收
            //用data将条件对象转换为json，封装到对应的bean中
            data: teacher
          })
    },

  //根据id查询讲师
  getTeacherInfo(id) {
    return request({
        url: `/eduservice/teacher/getTeacherById/${id}`,
        method: 'get'
      })
},

    //修改讲师
    updateTeacherInfo(teacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: teacher
          })
    }

}



