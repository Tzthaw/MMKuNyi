package com.example.ptut.mm_kunyi.vos

data class InterestedVO(
        val seekerName: String? = null,
        val interestedTimeStamp: String? = null,
        val seekerId: Int? = null,
        val seekerSkill: List<SeekerSkillIVO?>? = null,
        val seekerProfilePicUrl: String? = null
)
