import request from '@/utils/request'


export default{

    //获取所有课程分类树形结构
    getSubjectList(){
        return request({
            url: '/eduservice/subject/getAllSubject',
            method: 'get',
          })
    }
}



