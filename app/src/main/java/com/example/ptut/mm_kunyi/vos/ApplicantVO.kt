package com.example.ptut.mm_kunyi.vos

import android.R.attr.author
import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList


data class ApplicantVO(
        var seekerName: String? = null,
        var canLowerOfferAmount: Boolean? = null,
        var appliedDate: String? = null,
        var seekerId: Int? = null,
        var seekerSkill: List<SeekerSkillIVO?>? = null,
        var seekerProfilePicUrl: String? = null,
        var whyShouldHire: List<WhyShouldHireVO?>? = null
){
    companion object {
        fun initApplicant(name: String?,pictureUrl: String?):ApplicantVO{
            var applicantVO=ApplicantVO()
            applicantVO.seekerName=name
            applicantVO.seekerId=(System.currentTimeMillis()/100).toInt()
            applicantVO.canLowerOfferAmount=false
            applicantVO.appliedDate=Calendar.getInstance().time.toString()
            applicantVO.seekerSkill= ArrayList()
            applicantVO.seekerProfilePicUrl=pictureUrl
            applicantVO.whyShouldHire=ArrayList()
            return applicantVO
        }
    }
}


