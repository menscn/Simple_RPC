import request from '@/utils/request'

export default {
    //根据课程id查询章节、小节信息
    getChapterVideoById(courseId) {
        return request({
            url: `/eduservice/edu-chapter/getChapterVideoById/${courseId}`,
            method: 'get'
        })
    },
    // 添加章节
    addChapter(eduChapter) {
        return request({
            url: `/eduservice/edu-chapter/addChapter`,
            method: 'post',
            data: eduChapter
        })
    },
    //根据id删除章节
    delChapter(id) {
        return request({
            url: `/eduservice/edu-chapter/delChapter/${id}`,
            method: 'delete'
        })
    },
    //根据id查询章节
    getChapterById(id) {
        return request({
            url: `/eduservice/edu-chapter/getChapterById/${id}`,
            method: 'get'
        })
    },
    //修改章节
    updateChapter(eduChapter) {
        return request({
            url: `/eduservice/edu-chapter/updateChapter`,
            method: 'post',
            data: eduChapter
        })
    }
}