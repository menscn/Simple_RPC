import request from '@/utils/request'

export default {
    // 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/educourse/addCourseinfo`,
            method: 'post',
            data: courseInfo
        })
    },
    getCourseInfoById(id) {
        return request({
            url: `/eduservice/educourse/getCourseInfoById/${id}`,
            method: 'get'
        })
    },
    updateCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/educourse/updateCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    //根据课程id查询课程发布信息
    getCoursePublishById(id) {
        return request({
            url: `/eduservice/educourse/getCoursePublishById/${id}`,
            method: 'get'
        })
    },

    //根据id发布课程
    publishCourse(id) {
        return request({
            url: `/eduservice/educourse/publishCourse/${id}`,
            method: 'put'
        })
    },

    //查询所有课程信息
    getCourseInfo() {
        return request({
            url: `/eduservice/educourse/getCourseInfo`,
            method: 'get'
        })
    },
    //根据id删除课程相关信息
    delCourseInfo(id) {
        return request({
            url: `/eduservice/educourse/delCourseInfo/${id}`,
            method: 'delete'
        })
    }
}