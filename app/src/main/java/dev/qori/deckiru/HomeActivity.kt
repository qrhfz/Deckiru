package dev.qori.deckiru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import dev.qori.deckiru.databinding.ActivityHomeBinding
import dev.qori.deckiru.fragment.AddDeckDialog
import dev.qori.deckiru.model.Deck
import dev.qori.deckiru.model.FirebaseGoogleAuth.mGoogleSignInClient
import dev.qori.deckiru.model.SavedPreference
import java.sql.Timestamp


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val db = FirebaseFirestore.getInstance()
    val list = ArrayList<Deck>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            AddDeckDialog(this).show(supportFragmentManager, "AddDeckDialogFragment")
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this, gso)

        readDecks(db)
        listView()
    }

    private fun listView() {

        // Set the LayoutManager that this RecyclerView will use.
        binding.rvDeck.layoutManager = LinearLayoutManager(this)
        // Adapter class is initialized and list is passed in the param.
        val itemAdapter = DeckAdapter(this, list)
        // adapter instance is set to the recyclerview to inflate the items.
        binding.rvDeck.adapter = itemAdapter
    }

//    private fun getItemsList(): ArrayList<Deck> {
//        val list = ArrayList<Deck>()
//
//        for(i in 0..20){
//            list.add(Deck(name = "mahmud$i", lastSession = Timestamp(0), "mahmud@mail.com"))
//        }
//        return list
//    }

    private fun readDecks(db: FirebaseFirestore) {
        db.collection("decks").get()
            .addOnSuccessListener { result ->
                for (document in result){
//                    list.add(Deck(document.data.name))
                    Log.d("READ", "Datanya : ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("READ", "Error getting documents : $exception")
            }
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
}