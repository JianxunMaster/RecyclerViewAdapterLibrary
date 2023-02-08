package com.lijianxun.adapter.library

import androidx.annotation.LayoutRes
import com.lijianxun.adapter.library.delegate.CommonViewDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 给非dataBinding使用的默认适配器
 */
abstract class SimpleCommonViewAdapter<T>(@LayoutRes var layoutId: Int, data: MutableList<T>?) : BaseCommonAdapter<T>(data) {
    abstract fun convert(adapter: SimpleCommonViewAdapter<T>, holder: BaseViewHolder, item: T, position: Int)

    init {
        addViewDelegate(object : CommonViewDelegate<T> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(adapter: BaseCommonAdapter<T>, holder: BaseViewHolder, item: T, position: Int) {
                this@SimpleCommonViewAdapter.convert(this@SimpleCommonViewAdapter, holder, item, position)
            }
        })
    }
}