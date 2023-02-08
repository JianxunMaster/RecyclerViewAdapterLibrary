package com.lijianxun.adapter.library.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * dataBinding基类
 */
class BaseBinderHolder<T : ViewDataBinding>(itemView: View) : BaseViewHolder(itemView) {
    var dataBinding: T = DataBindingUtil.bind(itemView)!!
}