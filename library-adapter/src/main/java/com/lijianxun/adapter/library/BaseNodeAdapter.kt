package com.lijianxun.adapter.library

import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.util.receiveDefectList
import com.lijianxun.adapter.library.util.nodeForEach
import java.util.*

/**
 * 节点适配器（多布局）
 *
 * @param <T> 泛型，节点数据的泛型</T>
 */
open class BaseNodeAdapter<T>(data: MutableList<Node<T>>?) : BaseCommonAdapter<Node<T>>(data) {

    val showList: MutableList<Node<T>> = mutableListOf() // 当前显示的节点
    override fun getContentData(): MutableList<Node<T>> {
        return showList
    }

    /**
     * 设置节点数据
     */
    override fun setList(data: MutableList<Node<T>>?) {
        this.data = data
        refreshAllNode()
    }

    /**
     * 刷新所有节点
     */
    fun refreshAllNode() {
        initAdapter()
        notifyDataSetChanged()
    }

    /**
     * 初始化展开的数据
     */
    private fun initAdapter() {
        showList.clear()
        data?.nodeForEach({
            showList.add(it)
        }, isContinue = { !it.opened })
    }

    /**
     * 展开节点
     *
     * @param node 节点
     */
    fun openNode(node: Node<T>) {
        node.opened = true
        if (showList.contains(node)) {//已展示当前node
            //子节点
            val list = mutableListOf<Node<T>>()
            node.children?.nodeForEach({
                list.add(it)
            }, isContinue = { !it.opened })
            val index = showList.indexOf(node)
            showList.addAll(index + 1, list)
            notifyDataSetChanged()
        } else {
            refreshAllNode()
        }
    }

    /**
     * 收起节点
     *
     * @param node 节点
     */
    fun closeNode(node: Node<T>) {
        node.opened = false
        if (showList.contains(node)) {//已展示当前node
            //子节点
            val list = mutableListOf<Node<T>>()
            node.children?.nodeForEach({
                list.add(it)
            }, isContinue = { !it.opened })
            if (showList.containsAll(list)) {
                //不能直接removeAll，大数据会卡顿。改成：取差集，添加到集合中。
                val data = receiveDefectList(showList, list)
                showList.clear()
                showList.addAll(data)
                notifyDataSetChanged()
            } else {
                refreshAllNode()
            }
        } else {
            refreshAllNode()
        }
    }

    /**
     * 切换展开或收起节点
     *
     * @param node 节点
     */
    fun toggleOpenOrCloseNode(node: Node<T>) {
        if (node.opened) {
            closeNode(node)
        } else {
            openNode(node)
        }
    }

    /**
     * 展开所有层级的节点
     */
    fun openAllNode() {
        data?.nodeForEach({
            it.opened = true
        })
        refreshAllNode()
    }

    /**
     * 收起所有层级的节点
     */
    fun closeAllNode() {
        data?.nodeForEach({
            it.opened = false
        })
        refreshAllNode()
    }

    /**
     * 展开层级内的节点
     *
     * @param level 层级
     */
    fun openLevelNode(level: Int) {
        data?.nodeForEach({
            it.opened = it.getLevel() <= level - 1
        })
        refreshAllNode()
    }

    /**
     * 构造器
     *
     * @param context  上下文
     * @param rootList 节点根列表（所有数据）
     */
    init {
        initAdapter()
    }
}