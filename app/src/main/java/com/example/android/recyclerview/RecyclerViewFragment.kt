/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.recyclerview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.recyclerview.screen.RecyclerFragmentController
import com.example.android.recyclerview.screen.UiComponent
import org.jetbrains.anko.AnkoContext

/**
 * Demonstrates the use of [RecyclerView] with a [LinearLayoutManager] and a
 * [GridLayoutManager].
 */
class RecyclerViewFragment : Fragment() {

    private val uiComponent by lazy { UiComponent() }
    private lateinit var dataset: Array<String>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.let { uiComponent.mActivity = it }
        return context?.let {
            val create = AnkoContext.create(it, this)
            val rootView = uiComponent.createView(create).also {
                if (savedInstanceState != null) {
                    // Restore saved layout manager type.
                    uiComponent.currentLayoutManagerType = savedInstanceState
                            .getSerializable(KEY_LAYOUT_MANAGER) as UiComponent.LayoutManagerType
                }
                uiComponent.setRecyclerViewLayoutManager(uiComponent.mActivity, uiComponent.currentLayoutManagerType)
                val controller = RecyclerFragmentController()
                uiComponent.recyclerView.setController(controller)
                initDataset()
                controller.setData(dataset)
            }
            rootView
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, uiComponent.currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private fun initDataset() {
        dataset = Array(DATASET_COUNT, { i -> "This is element # $i" })
    }

    companion object {
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val DATASET_COUNT = 60
    }
}
