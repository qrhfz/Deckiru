package dev.qori.deckiru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.qori.deckiru.R

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
    }
}