package com.example.android.recyclerview.screen

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.airbnb.epoxy.EpoxyRecyclerView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.UI
import org.jetbrains.anko.custom.ankoView

// EpoxyRecyclerView extensions for Anko layout DSL.
inline fun ViewManager.epoxyRecyclerView() = epoxyRecyclerView {}

inline fun ViewManager.epoxyRecyclerView(init: EpoxyRecyclerView.() -> Unit): EpoxyRecyclerView =
        ankoView({ EpoxyRecyclerView(it) }, theme = 0, init = init)

inline fun View.UI(): AnkoContext<Context> = context.UI {}