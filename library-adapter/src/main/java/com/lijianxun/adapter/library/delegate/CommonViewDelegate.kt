package com.lijianxun.adapter.library.delegate

import android.view.View
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 给view使用的默认委托
 */
interface CommonViewDelegate<T> : BaseCommonDelegate<T, BaseViewHolder> {
    override fun getViewHolder(itemView: View): BaseViewHolder {
        return BaseViewHolder(itemView)
    }
}