package com.example.ptut.mm_kunyi.vos

import android.R.attr.author
import com.google.firebase.database.Exclude



data class ApplicantVO(
        var seekerName: String? = null,
        var canLowerOfferAmount: Boolean? = null,
        var appliedDate: String? = null,
        var seekerId: Int? = null,
        var seekerSkill: List<SeekerSkillIVO?>? = null,
        var seekerProfilePicUrl: String? = null,
        var whyShouldHire: List<WhyShouldHireVO?>? = null
)

