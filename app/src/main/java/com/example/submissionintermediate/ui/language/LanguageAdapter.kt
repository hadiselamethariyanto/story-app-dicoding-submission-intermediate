package com.example.submissionintermediate.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionintermediate.databinding.ItemLanguageBinding
import com.example.submissionintermediate.model.Language

class LanguageAdapter(private val list: List<Language>) :
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(language: Language) {
            binding.tvLanguage.text = language.name
            binding.tvLanguage.setCompoundDrawablesWithIntrinsicBounds(
                language.icon,
                null,
                null,
                null
            )

            itemView.setOnClickListener {
                onItemClickCallBack.onClick(language.code)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallBack {
        fun onClick(code: String)
    }
}