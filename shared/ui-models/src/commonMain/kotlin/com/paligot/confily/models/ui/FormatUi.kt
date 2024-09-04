package com.paligot.confily.models.ui

data class FormatUi(
    val id: String,
    val name: String,
    val time: Int
) {
    companion object {
        val quickie = FormatUi(id = "", name = "Quickie", time = 20)
        val conference = FormatUi(id = "", name = "Conference", time = 50)
    }
}