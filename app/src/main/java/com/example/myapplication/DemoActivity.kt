package com.example.myapplication

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.data.createTestData
import com.example.myapplication.data.createTestNodeData
import com.example.myapplication.databinding.ActivityDemoBinding
import com.example.myapplication.databinding.ItemTest1Binding
import com.example.myapplication.databinding.ItemTest2Binding
import com.example.myapplication.databinding.ItemTest3Binding
import com.lijianxun.adapter.library.*
import com.lijianxun.adapter.library.delegate.*
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 演示示例
 */
class DemoActivity : BaseActivity<ActivityDemoBinding>() {
    var isNode = false
    var isMultiType = false
    var isDataBinding = false
    var hasData = false
    var hasHeader = false
    var hasFooter = false
    var hasEmpty = false
    var hasItemDecoration = false
    var isHeaderAndEmpty = false
    var isFooterAndEmpty = false

    val commonData = createTestData()
    val nodeData = createTestNodeData()
    override fun getLayoutId(): Int {
        return R.layout.activity_demo
    }

    var mAdapter: BaseCommonAdapter<Any> = object : SimpleCommonViewAdapter<String>(R.layout.item_test_1, null) {
        override fun convert(adapter: SimpleCommonViewAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.textTv).text = item
        }
    } as BaseCommonAdapter<Any>
    val mItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(10, 10, 10, 10)
        }
    }

    override fun initView() {
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        dataBinding.recyclerView.adapter = mAdapter
        dataBinding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            isNode = isChecked
            initAdapter()
        }
        dataBinding.switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            isMultiType = isChecked
            initAdapter()
        }
        dataBinding.switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            isDataBinding = isChecked
            initAdapter()
        }
        dataBinding.switch4.setOnCheckedChangeListener { buttonView, isChecked ->
            hasData = isChecked
            if (isNode) {
                (mAdapter as BaseNodeAdapter<String>).setList(if (hasData) nodeData else null)
            } else {
                (mAdapter as BaseCommonAdapter<String>).setList(if (hasData) commonData else null)
            }
        }
        val header = object : WrapperViewDelegate<Any> {
            override fun getLayoutId(): Int {
                return R.layout.header_test_1
            }

            override fun convert(adapter: BaseCommonAdapter<Any>, holder: BaseViewHolder) {
            }
        }
        dataBinding.switch5.setOnCheckedChangeListener { buttonView, isChecked ->
            hasHeader = isChecked
            if (hasHeader) mAdapter.addHeaderDelegate(header) else mAdapter.removeHeaderDelegate(header)
            mAdapter.notifyDataSetChanged()
        }
        val footer = object : WrapperViewDelegate<Any> {
            override fun getLayoutId(): Int {
                return R.layout.footer_test_1
            }

            override fun convert(adapter: BaseCommonAdapter<Any>, holder: BaseViewHolder) {
            }
        }
        dataBinding.switch6.setOnCheckedChangeListener { buttonView, isChecked ->
            hasFooter = isChecked
            if (hasFooter) mAdapter.addFooterDelegate(footer) else mAdapter.removeFooterDelegate(footer)
            mAdapter.notifyDataSetChanged()
        }
        val empty = object : WrapperViewDelegate<Any> {
            override fun getLayoutId(): Int {
                return R.layout.empty_test
            }

            override fun convert(adapter: BaseCommonAdapter<Any>, holder: BaseViewHolder) {
            }
        }
        dataBinding.switch7.setOnCheckedChangeListener { buttonView, isChecked ->
            hasEmpty = isChecked
            mAdapter.setEmptyDelegate(if (hasEmpty) empty else null)
            mAdapter.notifyDataSetChanged()
        }
        dataBinding.switch8.setOnCheckedChangeListener { buttonView, isChecked ->
            hasItemDecoration = isChecked
            if (hasItemDecoration) {
                if (dataBinding.recyclerView.itemDecorationCount == 0)
                    dataBinding.recyclerView.addItemDecoration(mItemDecoration)
            } else {
                if (dataBinding.recyclerView.itemDecorationCount != 0)
                    dataBinding.recyclerView.removeItemDecoration(mItemDecoration)
            }
        }
        dataBinding.switch9.setOnCheckedChangeListener { buttonView, isChecked ->
            isHeaderAndEmpty = isChecked
            mAdapter.isShowHeaderEmpty = isHeaderAndEmpty
            mAdapter.notifyDataSetChanged()
        }
        dataBinding.switch10.setOnCheckedChangeListener { buttonView, isChecked ->
            isFooterAndEmpty = isChecked
            mAdapter.isShowFooterEmpty = isFooterAndEmpty
            mAdapter.notifyDataSetChanged()
        }
    }

    fun initAdapter() {
        dataBinding.switch5.isChecked = false
        dataBinding.switch6.isChecked = false
        dataBinding.switch7.isChecked = false
        dataBinding.switch8.isChecked = false
        dataBinding.switch9.isChecked = false
        dataBinding.switch10.isChecked = false
        val common = if (hasData) commonData else null
        val node = if (hasData) nodeData else null
        mAdapter = if (isNode) {
            if (isMultiType) {
                if (isDataBinding) {
                    // 节点 多布局 dataBinding
                    BaseNodeAdapter(node)
                            .addViewDelegate(object : NodeBinderDelegate<String, ItemTest1Binding> {

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
                            .addViewDelegate(object : NodeBinderDelegate<String, ItemTest2Binding> {

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
                            .addViewDelegate(object : NodeBinderDelegate<String, ItemTest3Binding> {

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
                } else {
                    //节点 多布局 view
                    BaseNodeAdapter(node)
                            .addViewDelegate(object : NodeViewDelegate<String> {

                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_1
                                }

                                override fun isViewType(item: Node<String>, position: Int): Boolean {
                                    return item.isRoot()
                                }

                                override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
                                    holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setImageResource(when (item.getSelectState()) {
                                        SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                                        SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                                        SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                                    })
                                    holder.itemView.findViewById<ImageView>(R.id.iv_open).setImageResource(when {
                                        item.isLeaf() -> 0
                                        item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                                        else -> R.drawable.ic_baseline_arrow_right_24
                                    })
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                                        item.setSelectStatePart2All2None()
                                        adapter.notifyDataSetChanged()
                                    }
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                                    holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
                                }
                            })
                            .addViewDelegate(object : NodeViewDelegate<String> {

                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_2
                                }

                                override fun isViewType(item: Node<String>, position: Int): Boolean {
                                    return !item.isRoot() && !item.isLeaf()
                                }

                                override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
                                    holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setImageResource(when (item.getSelectState()) {
                                        SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                                        SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                                        SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                                    })
                                    holder.itemView.findViewById<ImageView>(R.id.iv_open).setImageResource(when {
                                        item.isLeaf() -> 0
                                        item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                                        else -> R.drawable.ic_baseline_arrow_right_24
                                    })
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                                        item.setSelectStatePart2All2None()
                                        adapter.notifyDataSetChanged()
                                    }
                                    holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
                                }
                            })
                            .addViewDelegate(object : NodeViewDelegate<String> {

                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_3
                                }

                                override fun isViewType(item: Node<String>, position: Int): Boolean {
                                    return item.isLeaf()
                                }

                                override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
                                    holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setImageResource(when (item.getSelectState()) {
                                        SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                                        SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                                        SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                                    })
                                    holder.itemView.findViewById<ImageView>(R.id.iv_open).setImageResource(when {
                                        item.isLeaf() -> 0
                                        item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                                        else -> R.drawable.ic_baseline_arrow_right_24
                                    })
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                                    holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                                        item.setSelectStatePart2All2None()
                                        adapter.notifyDataSetChanged()
                                    }
                                    holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
                                }
                            })
                }
            } else {
                if (isDataBinding) {
                    //节点 单布局 dataBinding
                    object : SimpleNodeBinderAdapter<String, ItemTest1Binding>(R.layout.item_test_1, node) {
                        override fun convert(adapter: SimpleNodeBinderAdapter<String, ItemTest1Binding>, holder: BaseBinderHolder<ItemTest1Binding>, item: Node<String>, position: Int) {
                            holder.dataBinding.testLayout.setPadding(item.getLevel() * 50, 0, 0, 0)
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
                    }
                } else {
                    //节点 单布局 view
                    object : SimpleNodeViewAdapter<String>(R.layout.item_test_1, node) {
                        override fun convert(adapter: SimpleNodeViewAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
                            holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                            holder.itemView.findViewById<ImageView>(R.id.iv_select).setImageResource(when (item.getSelectState()) {
                                SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                                SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                                SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                            })
                            holder.itemView.findViewById<ImageView>(R.id.iv_open).setImageResource(when {
                                item.isLeaf() -> 0
                                item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                                else -> R.drawable.ic_baseline_arrow_right_24
                            })
                            holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                            holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                                item.setSelectStatePart2All2None()
                                adapter.notifyDataSetChanged()
                            }
                            holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
                        }
                    }
                }
            }
        } else {
            if (isMultiType) {
                if (isDataBinding) {
                    //普通 多布局 dataBinding
                    BaseCommonAdapter(common)
                            .addViewDelegate(object : CommonBinderDelegate<String, ItemTest1Binding> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_1
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 0
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest1Binding>, item: String, position: Int) {
                                    holder.dataBinding.textTv.text = item
                                }
                            })
                            .addViewDelegate(object : CommonBinderDelegate<String, ItemTest2Binding> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_2
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 1
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest2Binding>, item: String, position: Int) {
                                    holder.dataBinding.textTv.text = item
                                }
                            })
                            .addViewDelegate(object : CommonBinderDelegate<String, ItemTest3Binding> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_3
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 2
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest3Binding>, item: String, position: Int) {
                                    holder.dataBinding.textTv.text = item
                                }
                            })
                } else {
                    //普通 多布局 view
                    BaseCommonAdapter(common)
                            .addViewDelegate(object : CommonViewDelegate<String> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_1
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 0
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item
                                }
                            })
                            .addViewDelegate(object : CommonViewDelegate<String> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_2
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 1
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item
                                }
                            })
                            .addViewDelegate(object : CommonViewDelegate<String> {
                                override fun getLayoutId(): Int {
                                    return R.layout.item_test_3
                                }

                                override fun isViewType(item: String, position: Int): Boolean {
                                    return position % 3 == 2
                                }

                                override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                                    holder.itemView.findViewById<TextView>(R.id.textTv).text = item
                                }
                            })
                }
            } else {
                if (isDataBinding) {
                    //普通 单布局 dataBinding
                    object : SimpleCommonBinderAdapter<String, ItemTest1Binding>(R.layout.item_test_1, common) {
                        override fun convert(adapter: SimpleCommonBinderAdapter<String, ItemTest1Binding>, holder: BaseBinderHolder<ItemTest1Binding>, item: String, position: Int) {
                            holder.dataBinding.textTv.text = item
                        }
                    }
                } else {
                    //普通 单布局 view
                    object : SimpleCommonViewAdapter<String>(R.layout.item_test_1, common) {
                        override fun convert(adapter: SimpleCommonViewAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                            holder.itemView.findViewById<TextView>(R.id.textTv).text = item
                        }
                    }
                }
            }
        } as BaseCommonAdapter<Any>
        dataBinding.recyclerView.adapter = mAdapter
    }
}