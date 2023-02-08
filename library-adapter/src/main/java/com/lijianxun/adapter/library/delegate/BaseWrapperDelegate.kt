package com.lijianxun.adapter.library.delegate

import android.view.View
import androidx.annotation.LayoutRes
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 给view使用的默认委托
 */
interface BaseWrapperDelegate<T, V : BaseViewHolder> {
    /**
     * 指定适配器的viewType
     */
    fun getViewType(): Int {
        return getLayoutId()
    }

    /**
     * 当前根视图布局id
     */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * 构造ViewHolder
     * @param itemView 视图
     */
    fun getViewHolder(itemView: View): V

    /**
     * 视图适配
     * @param adapter 适配器
     * @param holder 视图持有者
     */
    fun convert(adapter: BaseCommonAdapter<T>, holder: V)
}