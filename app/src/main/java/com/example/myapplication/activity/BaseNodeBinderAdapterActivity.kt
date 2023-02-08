package com.example.myapplication.activity

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestNodeData
import com.example.myapplication.databinding.ItemTest1Binding
import com.example.myapplication.databinding.ItemTest2Binding
import com.example.myapplication.databinding.ItemTest3Binding
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.BaseNodeAdapter
import com.lijianxun.adapter.library.delegate.NodeBinderDelegate
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 节点适配器（dataBinding 多布局）
 */
class BaseNodeBinderAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        val adapter: BaseNodeAdapter<String> = BaseNodeAdapter(createTestNodeData())
        adapter.addViewDelegate(object : NodeBinderDelegate<String, ItemTest1Binding> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_1
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return item.isRoot()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseBinderHolder<ItemTest1Binding>, item: Node<String>, position: Int) {
                holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.dataBinding.ivSelect.setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.dataBinding.ivOpen.setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.dataBinding.ivSelect.setOnClickListener {
                    item.setSelectStatePart2None2All()
                    adapter.notifyDataSetChanged()
                }
                holder.dataBinding.textTv.text = item.data
                holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
            }
        })
        adapter.addViewDelegate(object : NodeBinderDelegate<String, ItemTest2Binding> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_2
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return !item.isRoot() && !item.isLeaf()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseBinderHolder<ItemTest2Binding>, item: Node<String>, position: Int) {
                holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.dataBinding.ivSelect.setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.dataBinding.ivOpen.setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.dataBinding.ivSelect.setOnClickListener {
                    item.setSelectStatePart2None2All()
                    adapter.notifyDataSetChanged()
                }
                holder.dataBinding.textTv.text = item.data
                holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
            }
        })
        adapter.addViewDelegate(object : NodeBinderDelegate<String, ItemTest3Binding> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_3
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return item.isLeaf()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseBinderHolder<ItemTest3Binding>, item: Node<String>, position: Int) {
                holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.dataBinding.ivSelect.setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.dataBinding.ivOpen.setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.dataBinding.ivSelect.setOnClickListener {
                    item.setSelectStatePart2None2All()
                    adapter.notifyDataSetChanged()
                }
                holder.dataBinding.textTv.text = item.data
                holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
            }
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}