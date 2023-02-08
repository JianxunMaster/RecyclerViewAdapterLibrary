package com.lijianxun.adapter.library.node

import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.util.nodeForEach

/**
 * 节点
 */
open class Node<T>(var data: T) {
    var parent: Node<T>? = null// 父节点
    var opened = false // 是否展开
    var selected = false// 是否选中
    var children: MutableList<Node<T>>? = null// 子集
        set(value) {
            field = value
            value?.forEach {
                addChild(it)
            }
        }

    /**
     * 添加子节点
     */
    fun addChild(child: Node<T>) {
        if (children == null) {
            children = mutableListOf()
        }
        children?.add(child)
        child.parent = this
    }

    /**
     * 节点层级
     */
    fun getLevel(): Int {
        var level = 0
        var p = parent
        while (p != null) {
            p = p.parent
            level++
        }
        return level
    }

    /**
     * 是否根节点
     */
    fun isRoot(): Boolean {
        return parent == null
    }

    /**
     * 是否叶子节点
     */
    fun isLeaf(): Boolean {
        return children.isNullOrEmpty()
    }

    /**
     * 节点的选择状态
     */
    fun getSelectState(): SelectState {
        if (isLeaf()) {
            return if (selected) SelectState.ALL else SelectState.NONE
        } else {
            var none = true
            var all = true
            children?.nodeForEach({
                if (it.isLeaf()) {
                    if (it.selected) {
                        none = false
                    } else {
                        all = false
                    }
                }
            })
            return when {
                all -> SelectState.ALL
                none -> SelectState.NONE
                else -> SelectState.PART
            }
        }
    }

    /**
     * 切换下一个选中状态：部分选->未选->全选
     */
    fun changeSelectStatePart2None2All() {
        if (isLeaf()) {
            selected = !selected
        } else {
            val state = getSelectState()
            val nextState = !(state == SelectState.PART || state == SelectState.ALL)
            children?.nodeForEach({
                if (it.isLeaf()) {
                    it.selected = nextState
                }
            })
        }
    }

    /**
     * 切换下一个选中状态：部分选->全选->未选
     */
    fun changeSelectStatePart2All2None() {
        if (isLeaf()) {
            selected = !selected
        } else {
            val state = getSelectState()
            val nextState = state == SelectState.PART || state == SelectState.NONE
            children?.nodeForEach({
                if (it.isLeaf()) {
                    it.selected = nextState
                }
            })
        }
    }
}