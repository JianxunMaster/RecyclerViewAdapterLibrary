package com.lijianxun.adapter.library.delegate

import android.view.View
import androidx.databinding.ViewDataBinding
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 给view使用的默认委托
 */
interface CommonBinderDelegate<T,V:ViewDataBinding> : BaseCommonDelegate<T, BaseBinderHolder<V>> {
    override fun getViewHolder(itemView: View): BaseBinderHolder<V> {
        return BaseBinderHolder(itemView)
    }
}