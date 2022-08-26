package com.example.submissionintermediate.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.submissionintermediate.R

class CustomButton : FrameLayout {

    private lateinit var tvText: TextView
    private lateinit var loading: ProgressBar
    private lateinit var cl: ConstraintLayout

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.custom_button, this)

        tvText = findViewById(R.id.tvText)
        loading = findViewById(R.id.loading)
        cl = findViewById(R.id.cl)

    }

    fun setText(text: String) {
        tvText.text = text
    }

    fun setEnable(isEnabled:Boolean){
        if (isEnabled){
            isClickable = true
            cl.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
        }else{
            isClickable = false
            cl.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_200))
        }
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            cl.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_200))
            tvText.visibility = View.GONE
            loading.visibility = View.VISIBLE
        } else {
            cl.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
            tvText.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }
    }
}