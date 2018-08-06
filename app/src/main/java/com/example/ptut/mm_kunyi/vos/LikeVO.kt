package com.example.ptut.mm_kunyi.vos

data class LikeVO(var likeId:Long?=null,var userId:String?=null,var timeStamp:Long?=null) {
    companion object {
        fun initLike(id: String):LikeVO{
            val like=LikeVO()
            like.likeId=System.currentTimeMillis()
            like.userId=id
            like.timeStamp=System.currentTimeMillis()
            return like
        }
    }
}