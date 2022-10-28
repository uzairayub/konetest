package com.tsl.elevator.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.text.DateFormat


class GsonUtil {
    val gson: Gson

    fun toJson(obj: Any?): String {
        return gson.toJson(obj)
    }

    class StringAdapter : TypeAdapter<String?>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String {
            if (reader.peek() === JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    companion object {
        private var instance: GsonUtil? = null
        fun ins(): GsonUtil? {
            if (instance == null) {
                instance = GsonUtil()
            }
            return instance
        }
    }

    init {
        gson = GsonBuilder()
            .registerTypeAdapter(String::class.java, StringAdapter())
            .serializeNulls()
            .setLenient()
            .setDateFormat(DateFormat.LONG)
            .create()
    }
}