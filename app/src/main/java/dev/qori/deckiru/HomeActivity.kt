package dev.qori.deckiru

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.qori.deckiru.databinding.ActivityHomeBinding
import dev.qori.deckiru.fragment.AddDeckDialog
import dev.qori.deckiru.fragment.UpdateDeckDialog
import dev.qori.deckiru.model.Deck
import dev.qori.deckiru.model.SavedPreference
import dev.qori.deckiru.utils.DeckViewHolder

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter : FirestoreRecyclerAdapter<Deck, DeckViewHolder>
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            AddDeckDialog(this).show(supportFragmentManager, "AddDeckDialogFragment").run {
                adapter.notifyDataSetChanged()
            }

        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this, gso)

        //readDecks(db)
        listView()

//        binding.rvDeck.
    }

    fun listView() {
        val query = db.collection("decks").whereEqualTo("owner", SavedPreference.getEmail(this))

        val res: FirestoreRecyclerOptions<Deck> = FirestoreRecyclerOptions.Builder<Deck>()
            .setQuery(query, Deck::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Deck, DeckViewHolder>(res) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.deck_list_row, parent, false)
                return DeckViewHolder(view)
            }

            override fun onBindViewHolder(holder: DeckViewHolder, position: Int, model: Deck) {
                val name = model.name
                val id = adapter.snapshots.getSnapshot(position).id
                holder.tvDeckROwItemName.text = name
                holder.itemView.setOnLongClickListener {
                    UpdateDeckDialog( adapter, id, name!!).show(supportFragmentManager, "UpdateDeckDialogFragment")
                    return@setOnLongClickListener true

                }
            }


        }

        binding.rvDeck.layoutManager = LinearLayoutManager(this)
        adapter.startListening()
        adapter.notifyDataSetChanged()
        binding.rvDeck.adapter =adapter

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


    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}