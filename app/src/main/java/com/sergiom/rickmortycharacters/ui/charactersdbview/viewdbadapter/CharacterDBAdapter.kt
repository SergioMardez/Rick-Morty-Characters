package com.sergiom.rickmortycharacters.ui.charactersdbview.viewdbadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.rickmortycharacters.databinding.CharacterRecyclerLayoutBinding

class CharacterDBAdapter(private val listener: ItemClickListener): RecyclerView.Adapter<CharacterDBViewHolder>() {
    private val items = ArrayList<CharacterDataBaseModel>()

    interface ItemClickListener {
        fun onItemClicked(item: CharacterDataBaseModel)
    }

    fun setItems(items: List<CharacterDataBaseModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDBViewHolder {
        val binding: CharacterRecyclerLayoutBinding = CharacterRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterDBViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterDBViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}