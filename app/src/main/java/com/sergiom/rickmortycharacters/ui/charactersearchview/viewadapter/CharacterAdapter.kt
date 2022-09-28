package com.sergiom.rickmortycharacters.ui.charactersearchview.viewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.rickmortycharacters.databinding.CharacterRecyclerLayoutBinding

class CharacterAdapter(private val listener: ItemClickListener): PagingDataAdapter<CharacterModel, CharacterViewHolder>(MediaModelDiff()) {
    private val items = ArrayList<CharacterDataBaseModel>()

    interface ItemClickListener {
        fun onItemClicked(item: CharacterModel)
    }

    fun setItems(items: List<CharacterDataBaseModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: CharacterRecyclerLayoutBinding = CharacterRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, listener)
        }
    }

    //override fun getItemCount(): Int = items.size
}

class MediaModelDiff : DiffUtil.ItemCallback<CharacterModel>() {
    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem == newItem
    }
}