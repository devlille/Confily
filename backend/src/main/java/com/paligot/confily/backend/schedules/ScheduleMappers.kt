package com.paligot.confily.backend.schedules

import com.paligot.confily.backend.internals.date.FormatterPattern
import com.paligot.confily.backend.internals.date.format
import com.paligot.confily.models.Info
import com.paligot.confily.models.PlanningItem
import com.paligot.confily.models.ScheduleItem
import com.paligot.confily.models.ScheduleItemV3
import com.paligot.confily.models.ScheduleItemV4
import com.paligot.confily.models.Session
import com.paligot.confily.models.Talk
import com.paligot.confily.models.inputs.ScheduleInput
import java.time.LocalDateTime

fun ScheduleDb.convertToPlanningTalkModel(talk: Talk) = PlanningItem.TalkItem(
    id = this.id,
    order = order ?: 0,
    startTime = this.startTime,
    endTime = this.endTime,
    room = this.room,
    talk = talk
)

fun ScheduleDb.convertToPlanningEventModel(info: Info) = PlanningItem.EventItem(
    id = this.id,
    order = order ?: 0,
    startTime = this.startTime,
    endTime = this.endTime,
    room = this.room,
    info = info
)

fun ScheduleDb.convertToModel(talk: Talk?) = ScheduleItem(
    id = this.id,
    order = order ?: 0,
    time = LocalDateTime.parse(this.startTime).format(FormatterPattern.HoursMinutes),
    startTime = this.startTime,
    endTime = this.endTime,
    room = this.room,
    talk = talk
)

fun ScheduleDb.convertToModelV3() = ScheduleItemV3(
    id = this.id,
    order = order ?: 0,
    date = LocalDateTime.parse(this.startTime).format(FormatterPattern.YearMonthDay),
    startTime = this.startTime,
    endTime = this.endTime,
    room = this.room,
    talkId = talkId
)

fun ScheduleDb.convertToModelV4() = ScheduleItemV4(
    id = this.id,
    order = order ?: 0,
    date = LocalDateTime.parse(this.startTime).format(FormatterPattern.YearMonthDay),
    startTime = this.startTime,
    endTime = this.endTime,
    room = this.room,
    sessionId = talkId ?: this.id
)

fun ScheduleItemV4.convertToEventSession(): Session = Session.Event(
    id = id,
    title = "Break",
    description = null,
    address = null
)

fun ScheduleInput.convertToDb(endTime: String, talkId: String? = null) = ScheduleDb(
    order = order,
    startTime = this.startTime,
    endTime = endTime,
    room = this.room,
    talkId = talkId
)
