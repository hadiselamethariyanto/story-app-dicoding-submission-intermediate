package com.example.submissionintermediate

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomInstrumentationRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, className, context)
    }
}