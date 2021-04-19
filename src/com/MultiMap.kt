package com


class MultiMap<K, V> {
    private val map: MutableMap<K, MutableCollection<V>?> = hashMapOf()
    fun put(key: K, value: V) {
        if (map[key] == null) map[key] = arrayListOf()
        map[key]!!.add(value)
    }

  fun values(key: K): Unit {
        var result: List<Any> =  map.getValue(key).toString().replace("[", "").replace("]", "").split(",").map {
            it.trim()
        }

        result.forEach()
        {
            println(it)
        }
    }

}
