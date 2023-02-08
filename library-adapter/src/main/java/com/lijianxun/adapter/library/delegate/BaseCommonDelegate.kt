package com.lijianxun.adapter.library.delegate

import android.view.View
import androidx.annotation.LayoutRes
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 委托基类
 */
interface BaseCommonDelegate<T, V : BaseViewHolder> {
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
     * 是否是当前类型
     *
     * @param item 数据
     * @return 当前类型
     */
    fun isViewType(item: T, position: Int): Boolean

    /**
     * 构造ViewHolder
     * @param itemView 视图
     */
    fun getViewHolder(itemView: View): V

    /**
     * 视图适配
     * @param adapter 适配器
     * @param holder 视图持有者
     * @param item 节点
     */
    fun convert(adapter: BaseCommonAdapter<T>, holder: V, item: T, position: Int)
}