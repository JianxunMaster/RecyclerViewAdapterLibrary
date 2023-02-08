package com.lijianxun.adapter.library.viewholder

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * viewHolder基类
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View>? = null
    private var mConvertView: View? = null

    init {

        mConvertView = itemView
        mViews = SparseArray()
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId 控件id
     * @return 控件
     */
    open fun <T : View> getView(@IdRes viewId: Int): T? {
        var view = mViews!![viewId]
        if (view == null) {
            view = mConvertView!!.findViewById(viewId)
            mViews!!.put(viewId, view)
        }
        return view as T?
    }

    open fun getConvertView(): View {
        return mConvertView!!
    }

}