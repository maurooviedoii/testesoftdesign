@file:Suppress("UNCHECKED_CAST")

package br.testesoftdesign.utils

import android.content.Context
import com.google.gson.Gson

object JsonUtils {

    fun getListCharacters(context: Context): List<String> {
        val jsonString: String
        return try {
            jsonString = context.assets.open("characters.json").bufferedReader()
                .use { it.readText() }
            Gson().fromJson(jsonString, List::class.java) as List<String>

        } catch (e: Exception) {
            emptyList()
        }
    }
}