package dev.qori.deckiru.model

import android.content.Context
import android.content.SharedPreferences

object SavedPreference {
    private const val EMAIL= "email"
    private const val USERNAME="username"



    private  fun getPref(ctx: Context): SharedPreferences? {
        return ctx.getSharedPreferences("dev.qori.deckiru_user", Context.MODE_PRIVATE)
    }

    private fun  editor(context: Context, const:String, string: String){
        getPref(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getPref(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getPref(
        context
    )?.getString(USERNAME,"")
}
