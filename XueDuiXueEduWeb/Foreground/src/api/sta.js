import request from '@/utils/request'

export default {
    
    //生成统计数据
    createStaDaily(day){
        return request({
            url: `/staservice/stadaily/createStaDaily/${day}`,
            method: 'post'
          })
    },
    //查询统计数据
    getStaDaily(searchObj){
        return request({
            url: `/staservice/stadaily/getStaDaily/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
          })
    }


}