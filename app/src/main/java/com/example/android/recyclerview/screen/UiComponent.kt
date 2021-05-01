package com.example.android.recyclerview.screen

import android.support.v4.app.FragmentActivity
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.android.recyclerview.R
import com.example.android.recyclerview.RecyclerViewFragment
import com.example.android.recyclerview.screen.UiComponent.LayoutManagerType.*
import org.jetbrains.anko.*

class UiComponent : AnkoComponent<RecyclerViewFragment> {
    lateinit var mActivity: FragmentActivity
    lateinit var recyclerView: EpoxyRecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var currentLayoutManagerType: UiComponent.LayoutManagerType

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun createView(ui: AnkoContext<RecyclerViewFragment>): View {
        return with(ui) {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)

                radioGroup {
                    orientation = HORIZONTAL
                    gravity = CENTER_HORIZONTAL
                    check(R.id.linear_layout_rb)

                    radioButton {
                        id = R.id.linear_layout_rb
                        text = ctx.getString(R.string.linear_layout_manager)
                        setOnClickListener { setRecyclerViewLayoutManager(mActivity, LINEAR_LAYOUT_MANAGER) }
                    }

                    radioButton {
                        id = R.id.grid_layout_rb
                        text = ctx.getString(R.string.grid_layout_manager)
                        setOnClickListener { setRecyclerViewLayoutManager(mActivity, GRID_LAYOUT_MANAGER) }
                    }
                }.lparams(width = matchParent)

                recyclerView = epoxyRecyclerView {
                    id = R.id.recyclerView
                    layoutManager = LinearLayoutManager(ctx)
                    currentLayoutManagerType = LINEAR_LAYOUT_MANAGER
                }.lparams(width = matchParent, height = matchParent)
            }
        }.apply { tag = TAG }
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    fun setRecyclerViewLayoutManager(activity: FragmentActivity, layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            GRID_LAYOUT_MANAGER -> {
                layoutManager = GridLayoutManager(activity, SPAN_COUNT)
                currentLayoutManagerType = GRID_LAYOUT_MANAGER
            }
            LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType = LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView) {
            layoutManager = this@UiComponent.layoutManager
            scrollToPosition(scrollPosition)
        }
    }

    companion object {
        const val TAG = "RecyclerViewFragment"
        private val SPAN_COUNT = 2
    }
}