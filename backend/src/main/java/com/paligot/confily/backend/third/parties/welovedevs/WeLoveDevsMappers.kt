package com.paligot.confily.backend.third.parties.welovedevs

import com.paligot.confily.backend.jobs.JobDb
import com.paligot.confily.backend.jobs.SalaryDb
import com.paligot.confily.models.Job
import com.paligot.confily.models.Salary

fun Hit.convertToDb(id: String, partnerId: String) = JobDb(
    id = id,
    partnerId = partnerId,
    url = "https://welovedevs.com/app/job/$objectID",
    title = this.title,
    companyName = this.smallCompany.companyName,
    location = this.formattedPlaces.first(),
    salary = this.details.salary?.let { salary ->
        SalaryDb(
            min = salary.min,
            max = salary.max,
            recurrence = salary.recurrence
        )
    },
    requirements = this.details.requiredExperience,
    propulsed = "WeLoveDevs",
    publishDate = publishDate
)

fun JobDb.convertToModel() = Job(
    url = url,
    title = title,
    companyName = companyName,
    location = location,
    salary = salary?.let { Salary(min = it.min, max = it.max, recurrence = it.recurrence) },
    requirements = requirements,
    propulsed = propulsed,
    publishDate = publishDate
)
