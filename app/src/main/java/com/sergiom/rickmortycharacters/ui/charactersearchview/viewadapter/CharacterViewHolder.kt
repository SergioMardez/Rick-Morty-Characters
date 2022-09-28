package com.sergiom.rickmortycharacters.ui.charactersearchview.viewadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.rickmortycharacters.R
import com.sergiom.rickmortycharacters.databinding.CharacterRecyclerLayoutBinding

class CharacterViewHolder(private val itemBinding: CharacterRecyclerLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: CharacterModel, listener: CharacterAdapter.ItemClickListener) {
        itemBinding.root.setOnClickListener {
            listener.onItemClicked(item)
        }
        itemBinding.characterName.text = item.name
        Glide.with(itemBinding.root.context)
            .load(item.image)
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .fallback(R.drawable.ic_placeholder)
            .into(itemBinding.characterImage)
    }
}