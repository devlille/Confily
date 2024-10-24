package com.paligot.confily.models.ui

data class UserProfileUi(
    val email: String,
    val firstName: String,
    val lastName: String,
    val company: String,
    val qrCode: ByteArray?
) {
    companion object {
        val fake = UserProfileUi(
            email = "gerard@gdglille.org",
            firstName = "Gérard",
            lastName = "Paligot",
            company = "Decathlon",
            qrCode = null
        )
    }
}
