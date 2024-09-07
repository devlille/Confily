package com.paligot.confily.backend.schedules

import com.paligot.confily.backend.categories.CategoryModule.categoryDao
import com.paligot.confily.backend.events.EventModule.eventDao
import com.paligot.confily.backend.formats.FormatModule.formatDao
import com.paligot.confily.backend.internals.InternalModule.database
import com.paligot.confily.backend.sessions.SessionModule.sessionDao
import com.paligot.confily.backend.speakers.SpeakerModule.speakerDao

object ScheduleModule {
    val scheduleItemDao = lazy { ScheduleItemDao(database.value) }
    val scheduleRepository = lazy {
        ScheduleRepository(
            eventDao.value,
            sessionDao.value,
            categoryDao.value,
            formatDao.value,
            speakerDao.value,
            scheduleItemDao.value
        )
    }
}