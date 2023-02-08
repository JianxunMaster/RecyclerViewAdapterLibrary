package com.lijianxun.adapter.library

import android.view.View
import androidx.annotation.LayoutRes
import com.lijianxun.adapter.library.delegate.BaseCommonDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 通用适配器（单布局）
 */
abstract class SimpleCommonAdapter<T, V : BaseViewHolder>(@LayoutRes var layoutId: Int, data: MutableList<T>?) : BaseCommonAdapter<T>(data) {
    abstract fun getViewHolder(itemView: View): V
    abstract fun convert(adapter: SimpleCommonAdapter<T, V>, holder: V, item: T, position: Int)

    init {
        addItemDelegate(object : BaseCommonDelegate<T, V> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun getViewHolder(itemView: View): V {
                return this@SimpleCommonAdapter.getViewHolder(itemView)
            }

            override fun convert(adapter: BaseCommonAdapter<T>, holder: V, item: T, position: Int) {
                this@SimpleCommonAdapter.convert(this@SimpleCommonAdapter, holder, item, position)
            }
        })
    }
}