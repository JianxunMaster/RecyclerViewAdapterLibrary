package com.lijianxun.adapter.library.delegate

import com.lijianxun.adapter.library.viewholder.BaseViewHolder
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.BaseNodeAdapter

/**
 * 给非dataBinding使用的默认树节点委托
 */
interface BaseNodeDelegate<T, V : BaseViewHolder> : BaseCommonDelegate<Node<T>, V> {
    override fun convert(adapter: BaseCommonAdapter<Node<T>>, holder: V, item: Node<T>, position: Int) {
        convert(adapter as BaseNodeAdapter<T>, holder, item, position)
    }

    /**
     * 视图适配
     *
     * @param holder   视图持有者
     * @param item 树节点
     */
    fun convert(adapter: BaseNodeAdapter<T>, holder: V, item: Node<T>, position: Int)
}