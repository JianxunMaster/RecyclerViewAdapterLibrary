package com.lijianxun.adapter.library

import androidx.databinding.ViewDataBinding
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.delegate.NodeBinderDelegate
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 节点适配器（dataBinding 单布局）
 */
abstract class SimpleNodeBinderAdapter<T, V : ViewDataBinding>(layoutId: Int, data: MutableList<Node<T>>?) : BaseNodeAdapter<T>(data) {
    abstract fun convert(adapter: SimpleNodeBinderAdapter<T, V>, holder: BaseBinderHolder<V>, item: Node<T>, position: Int)

    init {
        addItemDelegate(object : NodeBinderDelegate<T, V> {
            override fun getLayoutId(): Int {
                return layoutId
            }

            override fun isViewType(item: Node<T>, position: Int): Boolean {
                return true
            }

            override fun convert(adapter: BaseNodeAdapter<T>, holder: BaseBinderHolder<V>, item: Node<T>, position: Int) {
                this@SimpleNodeBinderAdapter.convert(this@SimpleNodeBinderAdapter, holder, item, position)
            }
        })
    }
}