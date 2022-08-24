import request from '@/utils/request'

export default {
    getTeacherPageVo(current, limit, teacherQuery) {
        return request({
            url: `/eduservice/eduteacher/getTeacherPage/${current}/${limit}`,
            method: 'post',
            data: teacherQuery  //转化json传递
        })
    },
    // 删除讲师
    delTeacher(id) {
        return request({
            url: `/eduservice/eduteacher/${id}`,
            method: 'delete',
            // data: teacherQuery  //转化json传递
        })
    },
    addTeacher(teacher) {
        return request({
            url: `/eduservice/eduteacher/addTeacher`,
            method: 'post',
            data: teacher  //转化json传递
        })
    },
    updateTeacher(teacher) {
        return request({
            url: `/eduservice/eduteacher/updateTeacher`,
            method: 'post',
            data: teacher  //转化json传递
        })
    },
    // 根据id查询
    getTeacherById(id) {
        return request({
            url: `/eduservice/eduteacher/getTeacherById/${id}`,
            method: 'get',
            // data: teacher  //转化json传递
        })
    },
    // 获得所有老师列表
    getAllTeacher() {
        return request({
            url: `/eduservice/eduteacher`,
            method: 'get',
            // data: teacher  //转化json传递
        })
    }
}