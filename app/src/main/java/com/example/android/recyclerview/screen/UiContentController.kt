package com.example.android.recyclerview.screen

import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.*
import com.example.android.common.logger.Log
import com.example.android.recyclerview.R
import com.example.android.recyclerview.screen.UiComponent.Companion.TAG
import org.jetbrains.anko.dimen
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView

@EpoxyModelClass
abstract class ItemModel: EpoxyModelWithView<View>() {
    @EpoxyAttribute
    lateinit var listItem: String
    @EpoxyAttribute
    var itemPosition: Int = 0

    data class Holder(val itemView: TextView)

    override fun buildView(parent: ViewGroup) = with(parent.UI()){
        frameLayout {
            lparams(width = matchParent, height = dimen(R.dimen.list_item_height)) {
                leftMargin = dimen(R.dimen.margin_medium)
                rightMargin = dimen(R.dimen.margin_medium)
                gravity = CENTER_VERTICAL
            }

            val item = textView(listItem) {
                id = R.id.textView
                setOnClickListener { Log.d(TAG, "Element $itemPosition clicked.") }
            }
            tag = Holder(item)
        }
    }

    override fun bind(view: View) {
        (view.tag as? Holder)?.apply {
            Log.d(TAG, "Element $itemPosition set.")
            itemView.text = listItem
        }
    }

    companion object {
        val TAG = ItemModel::class.java.simpleName
    }
}

class RecyclerFragmentController : TypedEpoxyController<Array<String>>() {
    override fun buildModels(data: Array<String>) {
        data.forEachIndexed { index, s ->
            item {
                id("element")
                listItem(s)
                itemPosition(index)
            }
        }
    }

}