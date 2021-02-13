package dev.qori.deckiru.model
import java.sql.Timestamp

data class Deck (
    val name : String,
    val lastSession : Timestamp,
    val owner : String
)