package dev.qori.deckiru.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import dev.qori.deckiru.R
import dev.qori.deckiru.databinding.UpdateDeckDialogFragmentBinding
import dev.qori.deckiru.model.Deck
import dev.qori.deckiru.utils.DeckViewHolder


class UpdateDeckDialog(private val adapter: FirestoreRecyclerAdapter<Deck, DeckViewHolder>, val id : String, val name: String) : DialogFragment() {
    private var updateDeckDialogFragmentBinding: UpdateDeckDialogFragmentBinding? = null
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        return inflater.inflate(R.layout.update_deck_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = UpdateDeckDialogFragmentBinding.bind(view)
        updateDeckDialogFragmentBinding = binding

        binding.etUpdateDeckName.setText(name)
        binding.btnUpdateDeck.setOnClickListener { updateDeck(binding.etUpdateDeckName.text.toString()); dialog?.dismiss() }
        binding.btnDelDeck.setOnClickListener{delDeck(); dialog?.dismiss()}

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun updateDeck(name: String) {

        db.collection("decks").document(id).update("name", name)
        adapter.notifyDataSetChanged()

    }

    private fun delDeck() {
        db.collection("decks").document(id).delete()
    }

    override fun onStop() {
        super.onStop()
        dismiss()

    }
}