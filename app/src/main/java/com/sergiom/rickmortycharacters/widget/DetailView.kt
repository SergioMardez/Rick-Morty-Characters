package com.sergiom.rickmortycharacters.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.rickmortycharacters.R
import com.sergiom.rickmortycharacters.databinding.DetailViewBinding

class DetailView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    var binding: DetailViewBinding = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setDetail(character: CharacterDataBaseModel?) {
        character?.let { char ->
            binding.apply {
                characterName.text = char.name
                characterStatus.background = root.context.getDrawable(checkStatus(char.status))
                characterStatus.text = root.context.getString(
                    R.string.character_detail_description_status, char.status, char.species
                )
                characterLocation.text = root.context.getString(
                    R.string.character_detail_location_text, char.location.name
                )
                characterOrigin.text = root.context.getString(
                    R.string.character_detail_origin_text, char.origin.name
                )
                Glide.with(this.root.context)
                    .load(char.image)
                    .transform(RoundedCorners(25))
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .fallback(R.drawable.ic_placeholder)
                    .into(characterImage)
            }
        }
    }

    private fun checkStatus(status: String): Int {
        return when (status) {
            "Dead" -> R.drawable.ic_baseline_circle_dead
            else -> R.drawable.ic_baseline_circle_alive
        }
    }

}