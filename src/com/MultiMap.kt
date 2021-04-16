package com.bank

class MultiMap<K, V> {
    private val map: MutableMap<K, MutableCollection<V>?> = hashMapOf()

    fun put(key: K, value: V) {
        if (map[key] == null) map[key] = arrayListOf()
        map[key]!!.add(value)
    }

    fun values(key: K): MutableCollection<V>? {
         return map.getValue(key)
    }
}