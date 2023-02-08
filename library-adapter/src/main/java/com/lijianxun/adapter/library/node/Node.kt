package com.lijianxun.adapter.library.node

import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.util.nodeForEach

/**
 * 树节点
 */
open class Node<T>(var data: T) {
    var parent: Node<T>? = null// 父类
    var opened = false // 是否展开
    var selected = false// 是否选中

    var children: MutableList<Node<T>>? = null
        // 子集
        set(value) {
            field = value
            value?.forEach {
                addChild(it)
            }
        }

    fun addChild(child: Node<T>) {
        if (children == null) {
            children = mutableListOf()
        }
        children?.add(child)
        child.parent = this
    }

    fun getLevel(): Int {
        var level = 0
        var p = parent
        while (p != null) {
            p = p.parent
            level++
        }
        return level
    }

    fun isRoot(): Boolean {
        return parent == null
    }

    fun isLeaf(): Boolean {
        return children.isNullOrEmpty()
    }

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
    fun setSelectStatePart2None2All() {
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
    fun setSelectStatePart2All2None() {
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