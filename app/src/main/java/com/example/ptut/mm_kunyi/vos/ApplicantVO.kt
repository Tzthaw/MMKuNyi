package com.example.ptut.mm_kunyi.vos

data class ApplicantVO(
        val seekerName: String? = null,
        val canLowerOfferAmount: Boolean? = null,
        val appliedDate: String? = null,
        val seekerId: Int? = null,
        val seekerSkill: List<SeekerSkillIVO?>? = null,
        val seekerProfilePicUrl: String? = null,
        val whyShouldHire: List<WhyShouldHireVO?>? = null
)
