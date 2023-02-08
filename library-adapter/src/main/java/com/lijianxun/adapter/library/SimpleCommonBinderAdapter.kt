package com.lijianxun.adapter.library

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.lijianxun.adapter.library.delegate.CommonBinderDelegate
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 给非dataBinding使用的默认适配器
 */
abstract class SimpleCommonBinderAdapter<T, V : ViewDataBinding>(@LayoutRes var layoutId: Int, data: MutableList<T>?) : BaseCommonAdapter<T>(data) {
    abstract fun convert(adapter: SimpleCommonBinderAdapter<T, V>, holder: BaseBinderHolder<V>, item: T, position: Int)

    init {
        addViewDelegate(object : CommonBinderDelegate<T, V> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(adapter: BaseCommonAdapter<T>, holder: BaseBinderHolder<V>, item: T, position: Int) {
                this@SimpleCommonBinderAdapter.convert(this@SimpleCommonBinderAdapter, holder, item, position)
            }
        })
    }
}