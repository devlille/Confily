package com.paligot.confily.core.di

import android.content.Context
import com.paligot.confily.core.Platform
import com.paligot.confily.core.QrCodeGenerator
import com.paligot.confily.core.QrCodeGeneratorAndroid
import com.paligot.confily.core.db.DatabaseWrapper
import com.paligot.confily.db.ConfilyDatabase
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Locale

actual val platformModule = module {
    single<ConfilyDatabase> { DatabaseWrapper(androidContext(), "confily.db").createDb() }
    single(named(TempFolderPathNamed)) {
        androidContext().cacheDir?.absolutePath?.toPath() ?: FileSystem.SYSTEM_TEMPORARY_DIRECTORY
    }
    single<Platform> { Platform() }
    single<ObservableSettings> {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences(
                /* name = */
                "com.paligot.confily.android",
                /* mode = */
                Context.MODE_PRIVATE
            )
        )
    }
    single<String>(named(AcceptLanguageNamed)) { Locale.getDefault().toLanguageTag() }
    single<QrCodeGenerator> { QrCodeGeneratorAndroid() }
}
