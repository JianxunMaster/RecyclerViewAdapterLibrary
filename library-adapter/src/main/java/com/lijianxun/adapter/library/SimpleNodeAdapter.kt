package com.lijianxun.adapter.library

import android.view.View
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.delegate.BaseNodeDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 树节点适配器（单布局）
 */
abstract class SimpleNodeAdapter<T, V : BaseViewHolder>(layoutId: Int, data: MutableList<Node<T>>?) : BaseNodeAdapter<T>(data) {
    abstract fun getViewHolder(itemView: View): V
    abstract fun convert(adapter: SimpleNodeAdapter<T, V>, holder: V, item: Node<T>, position: Int)

    init {
        addViewDelegate(object : BaseNodeDelegate<T, V> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: Node<T>, position: Int): Boolean {
                return true
            }

            override fun getViewHolder(itemView: View): V {
                return this@SimpleNodeAdapter.getViewHolder(itemView)
            }

            override fun convert(adapter: BaseNodeAdapter<T>, holder: V, item: Node<T>, position: Int) {
                this@SimpleNodeAdapter.convert(this@SimpleNodeAdapter, holder, item, position)
            }
        })
    }
}