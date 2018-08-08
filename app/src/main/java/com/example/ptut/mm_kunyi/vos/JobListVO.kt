package com.example.ptut.mm_kunyi.vos

import android.arch.persistence.room.*
import com.example.ptut.mm_kunyi.persistence.typeconverter.*
import java.util.ArrayList

@Entity(tableName = "jobList")
@TypeConverters(ImagesTypeConverter::class,ApplicantTypeConverter::class,RelevantTypeConverter::class,
		ViewedTypeConverter::class,RequiredSkillTypeConverter::class,InterestedTypeConverter::class,
		JobTagTypeConverter::class,LikeTypeConverter::class,CommentTypeConverter::class)
data class JobListVO(
		var jobId:String?=null,
		var images: List<String>? = null,
		@PrimaryKey
		var jobPostId: Int? = null,
		var genderForJob: Int? = null,
		@Embedded
		var jobDuration: JobDurationVO? = null,
		var isActive: Boolean? = null,
		var availablePostCount: Int? = null,
		var fullDesc: String? = null,
		var phoneNo: String? = null,
		var applicant: List<ApplicantVO>? = null,
		var postedDate: String? = null,
		var relevant: List<RelevantVO>? = null,
		var makeMoneyRating: Int? = null,
		var importantNotes: List<String>? = null,
		var viewed: List<ViewedVO>? = null,
		@Embedded
		var offerAmount: OfferAmount? = null,
		var location: String? = null,
		var requiredSkill: List<RequiredSkillVO>? = null,
		var shortDesc: String? = null,
		var interested: List<InterestedVO>? = null,
		var jobTags: List<JobTagsVO>? = null,
		var postClosedDate: String? = null,
		var email: String? = null,
		var like:List<LikeVO>?=null,
		@ColumnInfo(name = "comment")
		var comment:List<CommentVO>?=null
)
