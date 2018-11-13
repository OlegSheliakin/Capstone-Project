package com.home.olegsheliakin.corettools.resource

import android.content.Context
import android.graphics.drawable.Drawable

import androidx.core.content.ContextCompat

import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class ResourceProviderImpl @Inject
constructor(private val context: Context) : ResourceProvider {

    override fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}
