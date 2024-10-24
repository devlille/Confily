package com.paligot.confily.core.speakers

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import cafe.adriel.lyricist.Lyricist
import com.paligot.confily.db.ConfilyDatabase
import com.paligot.confily.models.ui.SpeakerItemUi
import com.paligot.confily.models.ui.SpeakerUi
import com.paligot.confily.models.ui.TalkItemUi
import com.paligot.confily.resources.Strings
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SpeakerDaoSQLDelight(
    private val db: ConfilyDatabase,
    private val lyricist: Lyricist<Strings>,
    private val dispatcher: CoroutineContext
) : SpeakerDao {
    override fun fetchSpeaker(eventId: String, speakerId: String): Flow<SpeakerUi> {
        return combine(
            db.speakerQueries.selectSpeaker(speakerId, eventId)
                .asFlow()
                .mapToOne(dispatcher),
            fetchTalksBySpeakerId(eventId, speakerId),
            transform = { speaker, talks ->
                return@combine speaker.convertToSpeakerUi(
                    talks = talks.toImmutableList(),
                    strings = lyricist.strings
                )
            }
        )
    }

    private fun fetchTalksBySpeakerId(
        eventId: String,
        speakerId: String
    ): Flow<ImmutableList<TalkItemUi>> = db.transactionWithResult {
        db.sessionQueries
            .selectTalksBySpeakerId(eventId, speakerId)
            .asFlow()
            .mapToList(dispatcher)
            .map { talks ->
                talks
                    .map { talk ->
                        talk.convertTalkItemUi(
                            session = db.sessionQueries
                                .selectSessionByTalkId(eventId, talk.id)
                                .executeAsOne(),
                            speakers = db.sessionQueries
                                .selectSpeakersByTalkId(eventId, talk.id)
                                .executeAsList(),
                            strings = lyricist.strings
                        )
                    }
                    .toImmutableList()
            }
    }

    override fun fetchSpeakers(eventId: String): Flow<ImmutableList<SpeakerItemUi>> =
        db.speakerQueries.selectSpeakersByEvent(eventId)
            .asFlow()
            .mapToList(dispatcher)
            .map { speakers ->
                speakers
                    .map { speaker -> speaker.convertToSpeakerItemUi(lyricist.strings) }
                    .toImmutableList()
            }
}
