package com.paligot.confily.backend.events

import com.paligot.confily.backend.NotAuthorized
import com.paligot.confily.backend.NotFoundException
import com.paligot.confily.backend.internals.helpers.database.BasicDatabase
import com.paligot.confily.backend.internals.helpers.database.get
import com.paligot.confily.backend.internals.helpers.database.getAll

class EventDao(
    private val projectName: String,
    private val database: BasicDatabase
) {
    suspend fun list(): List<EventDb> = database
        .getAll<EventDb>(projectName)
        .filter { it.published }

    suspend fun get(id: String): EventDb? = database.get(projectName, id)

    suspend fun getVerified(id: String, apiKey: String?): EventDb {
        val eventDb = database.get<EventDb>(projectName, id)
            ?: throw NotFoundException("Event $id Not Found")
        return if (eventDb.apiKey == apiKey) eventDb else throw NotAuthorized
    }

    suspend fun createOrUpdate(event: EventDb) {
        val existing = database.get<EventDb>(projectName, event.slugId)
        if (existing == null) {
            database.insert(projectName, event.slugId, event)
        } else {
            database.update(
                projectName,
                event.slugId,
                event.copy(updatedAt = System.currentTimeMillis())
            )
        }
    }

    suspend fun updateMenus(eventId: String, apiKey: String, menus: List<LunchMenuDb>) {
        val existing = getVerified(eventId, apiKey)
        database.update(
            projectName,
            eventId,
            existing.copy(menus = menus, updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun updateCoc(eventId: String, apiKey: String, coc: String) {
        val existing = getVerified(eventId, apiKey)
        database.update(
            projectName,
            eventId,
            existing.copy(coc = coc, updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun updateFeatures(eventId: String, apiKey: String, hasNetworking: Boolean) {
        val existing = getVerified(eventId, apiKey)
        database.update(
            projectName,
            eventId,
            existing.copy(
                features = FeaturesActivatedDb(hasNetworking = hasNetworking),
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateUpdatedAt(event: EventDb) {
        database.update(
            projectName,
            event.slugId,
            event.copy(updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun updateAgendaUpdatedAt(event: EventDb) {
        database.update(
            projectName,
            event.slugId,
            event.copy(agendaUpdatedAt = System.currentTimeMillis())
        )
    }
}
