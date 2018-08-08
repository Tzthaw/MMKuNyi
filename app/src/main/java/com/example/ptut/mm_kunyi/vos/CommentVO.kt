package com.example.ptut.mm_kunyi.vos

import java.util.*

data class CommentVO(
        var commentId:Long?=null,
        var userId:String?=null,
        var commentDate:String?=null,
        var commentUserName:String?=null,
        var commentUserPhoto:String?=null,
        var commentDetail:String?=null
) {
    companion object {
        fun initComment(userId:String,username:String,photoUrl:String,details:String):CommentVO{
            var commentVO=CommentVO()
            commentVO.commentId=System.currentTimeMillis()/100
            commentVO.userId=userId
            commentVO.commentDate= Calendar.getInstance().time.toString()
            commentVO.commentUserName=username
            commentVO.commentUserPhoto=photoUrl
            commentVO.commentDetail=details
            return commentVO
        }
    }
}