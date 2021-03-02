package dev.qori.deckiru

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.qori.deckiru.databinding.ActivityHomeBinding
import dev.qori.deckiru.fragment.AddDeckDialog
import dev.qori.deckiru.fragment.UpdateDeckDialog
import dev.qori.deckiru.model.Deck
import dev.qori.deckiru.model.FirebaseGoogleAuth.gso
import dev.qori.deckiru.model.SavedPreference
import dev.qori.deckiru.utils.DeckViewHolder

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val db = FirebaseFirestore.getInstance()
    private lateinit var query: Query
    private lateinit var res: FirestoreRecyclerOptions<Deck>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        query = db.collection("decks").whereEqualTo("owner", SavedPreference.getEmail(this))
        res = FirestoreRecyclerOptions.Builder<Deck>()
                .setQuery(query, Deck::class.java)
                .build()
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            AddDeckDialog(this).show(supportFragmentManager, "AddDeckDialogFragment")
        }

        mGoogleSignInClient = GoogleSignIn.getClient(applicationContext, gso)

        listView()
    }

    private fun listView() {

        val adapter = object : FirestoreRecyclerAdapter<Deck, DeckViewHolder>(res) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
                val view = LayoutInflater.from(this@HomeActivity)
                        .inflate(R.layout.deck_list_row, parent, false)
                return DeckViewHolder(view)
            }

            override fun onBindViewHolder(holder: DeckViewHolder, position: Int, model: Deck) {
                val name = model.name
                val id = this.snapshots.getSnapshot(position).id
                holder.tvDeckROwItemName.text = name
                holder.itemView.setOnClickListener{
                    addCard()
                }

                holder.itemView.setOnLongClickListener {
                    UpdateDeckDialog(this, id, name!!).show(supportFragmentManager, "UpdateDeckDialogFragment")
                    return@setOnLongClickListener true
                }
            }
        }

        binding.rvDeck.layoutManager = LinearLayoutManager(this)
        adapter.startListening()
        binding.rvDeck.adapter = adapter
    }

    //For Logout START
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return true
    }
    //For Logout END

    fun addCard(){
        val intent = Intent(this, AddCardActivity::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        binding.rvDeck.adapter = null
        super.onStop()
    }
}