package com.example.myapplication.data

import com.lijianxun.adapter.library.node.Node

fun createTestNodeData(): MutableList<Node<String>> {
    val list: MutableList<Node<String>> = mutableListOf()
    for (i in 0..9) {
        val node0 = Node("0级:$i")
        list.add(node0)
        for (j in 0..9) {
            val node1 = Node("1级:$j")
            node0.addChild(node1)
            for (k in 0..9) {
                val node2 = Node("2级:$k")
                node1.addChild(node2)
                for (l in 0..9) {
                    val node3 = Node("3级:$l")
                    node2.addChild(node3)
                }
            }
        }
    }
    return list
}

fun createTestData(): MutableList<String> {
    val list = mutableListOf<String>()
    for (i in 0..99) {
        list.add("" + i)
    }
    return list
}