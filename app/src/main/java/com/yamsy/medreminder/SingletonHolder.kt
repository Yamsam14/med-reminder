package com.yamsy.medreminder

open class SingletonHolder<out T: Any, in A>(private val constructor: (A) -> T) {

    @Volatile
    private var INSTANCE: T? = null

    fun getInstance(arg: A) : T {
        return when {
            INSTANCE != null -> INSTANCE!!
            else -> synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = constructor(arg)
                }
                INSTANCE!!
            }
        }
    }
}