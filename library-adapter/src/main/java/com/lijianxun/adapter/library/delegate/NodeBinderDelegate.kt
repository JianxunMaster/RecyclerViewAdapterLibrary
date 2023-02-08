package com.lijianxun.adapter.library.delegate

import android.view.View
import androidx.databinding.ViewDataBinding
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 给非dataBinding使用的默认委托
 */
interface NodeBinderDelegate<T, V : ViewDataBinding> : BaseNodeDelegate<T, BaseBinderHolder<V>> {
    override fun getViewHolder(itemView: View): BaseBinderHolder<V> {
        return BaseBinderHolder(itemView)
    }
}