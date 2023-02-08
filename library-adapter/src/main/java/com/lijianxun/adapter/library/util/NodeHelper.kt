package com.lijianxun.adapter.library.util

import com.lijianxun.adapter.library.node.Node
import java.util.*


/**
 * 遍历所有元素
 * 使用非递归方法
 * @param forEach 遍历元素
 * @param isBreak 是否退出方法
 * @param isContinue 是否跳过子节点遍历
 */
fun <T> MutableList<Node<T>>.nodeForEach(forEach: (Node<T>) -> Unit, isBreak: (Node<T>) -> Boolean = { false }, isContinue: (Node<T>) -> Boolean = { false }) {
    val stack = Stack<Node<T>>()
    this.reversed().forEach {
        stack.push(it)
    }
    while (stack.isNotEmpty()) {
        val element = stack.pop()
        forEach.invoke(element)
        if (isBreak.invoke(element)) {
            return
        }
        if (!isContinue.invoke(element)) {
            element.children?.reversed()?.forEach {
                stack.push(it)
            }
        }
    }
}

/**
 * @方法描述：获取两个ArrayList的差集
 * @param firstArrayList 第一个ArrayList
 * @param secondArrayList 第二个ArrayList
 * @return resultList 差集ArrayList
 */
fun <T> receiveDefectList(firstArrayList: List<T>, secondArrayList: List<T>): List<T> {
    val result = LinkedList(firstArrayList) // 大集合用linkedlist
    val othHash = HashSet(secondArrayList) // 小集合用hashset
    val it = result.iterator() // 采用Iterator迭代器进行数据的操作
    while (it.hasNext()) {
        if (othHash.contains(it.next())) {
            it.remove()
        }
    }
    return ArrayList(result)
}

/**
 * @方法描述：获取两个ArrayList的交集
 * @param firstArrayList 第一个ArrayList
 * @param secondArrayList 第二个ArrayList
 * @return resultList 交集ArrayList
 */
fun <T> receiveCollectionList(firstArrayList: List<T>, secondArrayList: List<T>): List<T> {
    val result = LinkedList(firstArrayList) // 大集合用linkedlist
    val othHash = HashSet(secondArrayList) // 小集合用hashset
    val it = result.iterator() // 采用Iterator迭代器进行数据的操作
    while (it.hasNext()) {
        if (!othHash.contains(it.next())) {
            it.remove()
        }
    }
    return ArrayList(result)
}

/**
 * @方法描述：获取两个ArrayList的去重并集
 * @param firstArrayList 第一个ArrayList
 * @param secondArrayList 第二个ArrayList
 * @return resultList 去重并集ArrayList
 */
fun <T> receiveUnionList(firstArrayList: List<T>, secondArrayList: List<T>): List<T> {
    val firstSet: MutableSet<T> = TreeSet(firstArrayList)
    for (id in secondArrayList) {
        // 当添加不成功的时候 说明firstSet中已经存在该对象
        firstSet.add(id)
    }
    return ArrayList<T>(firstSet)
}