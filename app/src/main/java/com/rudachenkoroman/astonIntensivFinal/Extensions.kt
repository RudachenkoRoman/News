package com.rudachenkoroman.astonIntensivFinal

import android.os.Build
import android.os.Bundle
import java.io.Serializable


    fun <T : Serializable> Bundle.getSerializableCompat(key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getSerializable(key,clazz)
        } else {
            (this.getSerializable(key) as T)
        }
    }
