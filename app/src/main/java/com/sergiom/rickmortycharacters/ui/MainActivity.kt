package com.sergiom.rickmortycharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergiom.rickmortycharacters.R
import com.sergiom.rickmortycharacters.ui.charactersearchview.CharacterSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}