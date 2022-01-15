package com.kuzmin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuzmin.model.PostModel
import java.io.File
import java.util.ArrayList

object PostData {
    fun getDataBase(): ArrayList<PostModel> {
        return try {
            val fileName = "post.json"
            val type = object : TypeToken<List<PostModel>>(){}.type
            val result : ArrayList<PostModel> = Gson().fromJson(File(fileName).readText(), type)
            result
        } catch (e: Exception){
            ArrayList<PostModel>()
        }
    }
}