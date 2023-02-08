# RecyclerViewAdapterLibrary

# 简介

   1.通用列表适配器

   2.节点列表适配器（多级列表适配器）

# 演示和使用

    演示安装包：file/apk-release.apk

    依赖库aar：file/library-adapter-release.aar
  
# 适配器介绍

    1.通用列表适配器

        1）添加装饰器：头部-header / 尾部-footer / 占位图-empty

        2）布局的支持类型：View / DataBinding

        3）支持ViewType：单布局 / 多布局

    2.节点列表适配器（支持通用列表适配器的功能）

        1）节点的展开和收起

        2）节点的选中状态：全选、不分选、未选

        3）节点展开到指定层

# 用法介绍

    简单说明：

        1 通用适配器

            1.1 通用适配器（多布局）

                BaseCommonAdapter

                添加viewType方法：addItemDelegate(BaseCommonDelegate)

                BaseCommonDelegate；CommonBinderDelegate（dataBinding布局）；CommonViewDelegate（view布局）；

            1.2 通用适配器（单布局）

                SimpleCommonAdapter；SimpleCommonBinderAdapter（dataBinding布局）（推荐）；SimpleCommonViewAdapter（view布局）（推荐）；

        2 节点适配器

            2.1 节点Node的常用方法

                添加子节点
                addChild(child: Node<T>)

                节点层级
                getLevel()

                是否根节点
                isRoot()

                是否叶子节点
                isLeaf()

                节点的选择状态
                getSelectState()

                切换下一个选中状态：部分选->未选->全选
                changeSelectStatePart2None2All()

                切换下一个选中状态：部分选->全选->未选
                changeSelectStatePart2All2None()

            2.2 节点适配器的常用方法

                设置节点数据
                setList(data: MutableList<Node<T>>?)

                刷新所有节点
                refreshAllNode()

                展开节点
                openNode(node: Node<T>)

                收起节点
                closeNode(node: Node<T>)

                切换展开或收起节点
                toggleOpenOrCloseNode(node: Node<T>)

                展开所有层级的节点
                openAllNode()

                收起所有层级的节点
                closeAllNode()

                展开层级内的节点
                openLevelNode(level: Int)

            2.3 节点适配器（多布局）

                BaseNodeAdapter

                添加viewType方法：addItemDelegate(BaseCommonDelegate)

                BaseNodeDelegate；NodeBinderDelegate（dataBinding布局）；NodeViewDelegate（view布局）；

            2.4 节点适配器（单布局）

                SimpleNodeAdapter；SimpleNodeBinderAdapter（dataBinding布局）（推荐）；SimpleNodeViewAdapter（view布局）（推荐）；

        3.添加装饰器： 头部-header / 尾部-footer / 占位图-empty

            添加头部：addHeaderDelegate；

            添加尾部：addFooterDelegate；

            设置占位图：setEmptyDelegate；

            BaseWrapperDelegate、WrapperBinderDelegate（dataBinding布局）、WrapperViewDelegate（view布局）
  
    需要注意的点：

        1.布局类型Delegate#isViewType：所有data对应的isViewType都能在Delegate中找到。

        2.Delegate的viewType不能重复：ViewType不能使用相同的布局，除非重写viewType，且值不能相同。因为：一个Delegate就是一个viewType，适配器的viewType是根据Delegate的viewType决定的。
    
# 欢迎加群讨论

  QQ群号：464635057
  
  个人开发，维护不易。如有问题，请多谅解。 ^_^
