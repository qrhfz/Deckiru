package dev.qori.deckiru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import dev.qori.deckiru.databinding.ActivityAddCardBinding
import dev.qori.deckiru.model.Card
import dev.qori.deckiru.model.Const.DECK_ID

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCardBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val message = intent.getStringExtra(DECK_ID)

        binding.deckId.text = message
        binding.addCardSubmit.setOnClickListener{
            submitNewCard(message!!)
        }
    }

    fun submitNewCard(id:String){
        db.collection("decks").document(id).collection("cards").document().set(Card("depan","belakang", "deskripsi", 0))
    }
}