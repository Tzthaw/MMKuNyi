package com.example.ptut.mm_kunyi.vos

data class JobDurationVO(
		var jobEndDate: String? = null,
		var jobStartDate: String? = null,
		var workingHoursPerDay: Int? = null,
		var workingDaysPerWeek: Int? = null,
		var totalWorkingDays: Int? = null
)
