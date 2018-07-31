package com.example.ptut.mm_kunyi.vos

data class ViewedVO(
        val viewedTimeStamp: String? = null,
        val seekerName: String? = null,
        val seekerId: Int? = null,
        val seekerSkill: List<SeekerSkillIVO?>? = null,
        val seekerProfilePicUrl: String? = null
):BaseVO()
