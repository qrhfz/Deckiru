package dev.qori.deckiru.model

import com.google.firebase.Timestamp

class Deck {
    var name: String? = null
    var id: String? = null
    var lastSession: Timestamp? = null
    var owner: String? = null

    constructor(){}
    constructor(name: String, lastSession: Timestamp, owner:String){
        this.name = name
        this.lastSession = lastSession
        this.owner = owner
    }
}