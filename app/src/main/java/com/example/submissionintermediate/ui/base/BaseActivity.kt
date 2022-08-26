package com.example.submissionintermediate.ui.base

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.utils.ContextUtils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private val userPreference: UserPreference by inject()

    override fun attachBaseContext(newBase: Context) {
        var languageCode = "id"
        lifecycleScope.launch {
            languageCode = userPreference.getLanguage().first()
        }
        val locale = Locale(languageCode)
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, locale)
        super.attachBaseContext(localeUpdatedContext)
    }
}