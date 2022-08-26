package com.example.submissionintermediate.ui.language

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityChangeLanguageBinding
import com.example.submissionintermediate.model.Language
import com.example.submissionintermediate.ui.base.BaseActivity
import kotlin.collections.ArrayList
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeLanguageActivity : BaseActivity() {

    private lateinit var binding: ActivityChangeLanguageBinding
    private val viewModel:ChangeLanguageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_change_language)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val list = ArrayList<Language>()
        list.add(
            Language(
                "Indonesia",
                "id",
                ContextCompat.getDrawable(this, R.drawable.ic_indonesia_flag)
            )
        )
        list.add(Language("English", "en", ContextCompat.getDrawable(this, R.drawable.ic_uk)))
        list.add(Language("Dutch", "nl", ContextCompat.getDrawable(this, R.drawable.ic_netherland)))

        val languageAdapter = LanguageAdapter(list)
        languageAdapter.setOnItemClickCallback(object :LanguageAdapter.OnItemClickCallBack{
            override fun onClick(code: String) {
                viewModel.changeLanguage(code)
                finish()
            }
        })

        binding.rvLanguage.adapter = languageAdapter
        binding.rvLanguage.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}