package dev.qori.deckiru.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import dev.qori.deckiru.R
import dev.qori.deckiru.databinding.AddDeckDialogFragmentBinding
import dev.qori.deckiru.model.Deck
import dev.qori.deckiru.model.SavedPreference


class AddDeckDialog(val ctx: Context) : DialogFragment() {
    private var addDeckDialogFragmentBinding: AddDeckDialogFragmentBinding? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        return inflater.inflate(R.layout.add_deck_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = AddDeckDialogFragmentBinding.bind(view)
        addDeckDialogFragmentBinding = binding

        binding.btnAddDeckSubmit.setOnClickListener { addDeck(this.db, binding.etAddDeckName.text.toString()); dialog?.dismiss() }

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun addDeck(db: FirebaseFirestore, name: String) {
        val deck = Deck(name = name, lastSession = Timestamp.now(), owner = SavedPreference.getEmail(ctx)!! )
        db.collection("decks").add(deck)
            .addOnSuccessListener { documentReference ->
                Log.d("WRITE", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.w("WRITE", "Error adding document : ${exception.message}")
            }
    }
}