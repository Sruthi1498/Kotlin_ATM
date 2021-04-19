package com.bank

import java.util.*
import java.util.regex.Pattern

class MultiMap<K, V> {
    private val map: MutableMap<K, MutableCollection<V>?> = hashMapOf()
    fun put(key: K, value: V) {
        if (map[key] == null) map[key] = arrayListOf()
        map[key]!!.add(value)
    }

    fun values(key: K): Unit {
         val s =  map.getValue(key).toString().replace("[","")
        val str = s.replace("]","")
        var result: List<Any> = str.split(",").map { it.trim() }

        result.forEach { println(it) }
    }


}