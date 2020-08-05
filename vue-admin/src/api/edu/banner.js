import request from '@/utils/request'


export default{

    //banner列表 (组合条件查询带分页)
    //current当前页，size每页显示条数，
    getBannerList(current,size){
        return request({
            url: `/educms/banneradmin/pageBanner/${current}/${size}`,
            method: 'get'
          })
    },

    //删除banner
    deleteBanner(id){
        return request({
            url: `/educms/banneradmin/remove/${id}`,
            method: 'delete'
          })
    },

    //添加banner
    addBanner(banner){
        return request({
            url: `/educms/banneradmin/save`,
            method: 'post',
            data: banner
          })
    },


    //修改banner
    updateBanner(banner) {
        return request({
            url: `/educms/banneradmin/update`,
            method: 'put',
            data: banner
          })
    },

    //根据id查banner
    getId(id) {
        return request({
            url: `/educms/banneradmin/get/${id}`,
            method: 'get'
          })
    }

}



