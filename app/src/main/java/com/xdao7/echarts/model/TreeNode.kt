package com.xdao7.echarts.model

data class TreeNode(
    val name: String,
    val value: Float,
    var children: List<TreeNode>? = null
)
