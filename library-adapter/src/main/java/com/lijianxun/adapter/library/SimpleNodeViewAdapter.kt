package com.lijianxun.adapter.library

import com.lijianxun.adapter.library.delegate.NodeViewDelegate
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 *使用view的单布局适配器
 */
abstract class SimpleNodeViewAdapter<T>(layoutId: Int, data: MutableList<Node<T>>?) : BaseNodeAdapter<T>(data) {
    abstract fun convert(adapter: SimpleNodeViewAdapter<T>, holder: BaseViewHolder, item: Node<T>, position: Int)

    init {
        addViewDelegate(object : NodeViewDelegate<T> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: Node<T>, position: Int): Boolean {
                return true
            }

            override fun convert(adapter: BaseNodeAdapter<T>, holder: BaseViewHolder, item: Node<T>, position: Int) {
                this@SimpleNodeViewAdapter.convert(this@SimpleNodeViewAdapter, holder, item, position)
            }
        })
    }
}