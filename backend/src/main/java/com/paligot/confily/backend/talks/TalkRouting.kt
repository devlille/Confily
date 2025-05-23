package com.paligot.confily.backend.talks

import com.paligot.confily.backend.internals.plugins.PlanningUpdatedAtPlugin
import com.paligot.confily.backend.receiveValidated
import com.paligot.confily.backend.talks.TalkModule.talkRepository
import com.paligot.confily.models.inputs.TalkInput
import com.paligot.confily.models.inputs.TalkVerbatimInput
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.registerTalksRoutes() {
    val repository by talkRepository

    get("/talks") {
        val eventId = call.parameters["eventId"]!!
        call.respond(HttpStatusCode.OK, repository.list(eventId))
    }
    get("/talks/{id}") {
        val eventId = call.parameters["eventId"]!!
        val talkId = call.parameters["id"]!!
        call.respond(HttpStatusCode.OK, repository.get(eventId, talkId))
    }
}

fun Route.registerAdminTalksRoutes() {
    val repository by talkRepository

    route("/talks") {
        this.install(PlanningUpdatedAtPlugin)
        post {
            val eventId = call.parameters["eventId"]!!
            val talkInput = call.receiveValidated<TalkInput>()
            call.respond(HttpStatusCode.Created, repository.create(eventId, talkInput))
        }
        put("/{id}") {
            val eventId = call.parameters["eventId"]!!
            val talkId = call.parameters["id"]!!
            val talkInput = call.receiveValidated<TalkInput>()
            call.respond(HttpStatusCode.OK, repository.update(eventId, talkId, talkInput))
        }
        post("/verbatim") {
            val eventId = call.parameters["eventId"]!!
            val verbatim = call.receiveValidated<TalkVerbatimInput>()
            val verbatims = repository.verbatim(eventId, verbatim)
            call.respond(
                status = if (verbatims.isEmpty()) HttpStatusCode.NoContent else HttpStatusCode.Created,
                message = verbatims
            )
        }
        post("/verbatim/permissions") {
            val eventId = call.parameters["eventId"]!!
            val verbatim = call.receiveValidated<TalkVerbatimInput>()
            val verbatims = repository.verbatimPermissions(eventId, verbatim)
            call.respond(
                status = if (verbatims.isEmpty()) HttpStatusCode.NoContent else HttpStatusCode.Created,
                message = verbatims
            )
        }
        post("/{id}/verbatim") {
            val eventId = call.parameters["eventId"]!!
            val talkId = call.parameters["id"]!!
            val verbatim = call.receiveValidated<TalkVerbatimInput>()
            call.respond(HttpStatusCode.Created, repository.verbatim(eventId, talkId, verbatim))
        }
        post("/{id}/verbatim/permissions") {
            val eventId = call.parameters["eventId"]!!
            val talkId = call.parameters["id"]!!
            val verbatim = call.receiveValidated<TalkVerbatimInput>()
            call.respond(HttpStatusCode.Created, repository.verbatimPermissions(eventId, talkId, verbatim))
        }
    }
}
