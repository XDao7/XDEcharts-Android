package com.xdao7.echarts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdao7.echarts.dsl.EchartsFunction
import com.xdao7.echarts.dsl.EchartsUnit
import com.xdao7.echarts.dsl.echarts
import com.xdao7.echarts.dsl.item
import com.xdao7.echarts.dsl.items
import com.xdao7.echarts.model.DemoModel
import com.xdao7.echarts.model.TreeNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class MainViewModel : ViewModel() {

    private val _line = MutableStateFlow("")
    private val _bar = MutableStateFlow("")
    private val _pie = MutableStateFlow("")
    private val _candlestick = MutableStateFlow("")
    private val _radar = MutableStateFlow("")
    private val _tree = MutableStateFlow("")
    val line = _line.asStateFlow()
    val bar = _bar.asStateFlow()
    val pie = _pie.asStateFlow()
    val candlestick = _candlestick.asStateFlow()
    val radar = _radar.asStateFlow()
    val tree = _tree.asStateFlow()

    private var echartsHtml = ""

    fun init(context: Context) {
        viewModelScope.launch {
            echartsHtml = context.assets.open("echarts.html").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).run {
                    buildString {
                        var line: String?
                        while ((readLine().also { line = it }) != null) {
                            append(line)
                        }
                    }
                }
            }
            createLine()
            createBar()
            createPie()
            createCandlestick()
            createRadar()
            createTree()
        }
    }

    private fun replaceEchartsHtml(option: String) = echartsHtml.replace("{option}", option)

    private fun createLine() {
        _line.value = replaceEchartsHtml(
            echarts {
                "xAxis" to {
                    "type" to "category"
                    "boundaryGap" to false
                    "data" to listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                }
                "yAxis" to {
                    "type" to "value"
                }
                "series" toList {
                    item {
                        "data" to listOf(820, 932, 901, 934, 1290, 1330, 1320)
                        "type" to "line"
                        "areaStyle" to {}
                    }
                }
            }
        )
    }

    private fun createBar() {
        _bar.value = replaceEchartsHtml(
            echarts {
                "xAxis" to {
                    "type" to "category"
                    "data" to listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                }
                "yAxis" to {
                    "type" to "value"
                }
                "series" toList {
                    item {
                        "data" to listOf(120, 200, 150, 80, 70, 110, 130)
                        "type" to "bar"
                    }
                }
            }
        )
    }

    private fun createPie() {
        val pieDataList = listOf(
            DemoModel("Search Engine", 1048),
            DemoModel("Direct", 735),
            DemoModel("Email", 580),
            DemoModel("Union Ads", 484),
            DemoModel("Video Ads", 300)
        )
        _pie.value = replaceEchartsHtml(
            echarts {
                "tooltip" to {
                    "trigger" to "item"
                    "triggerOn" to "click"
                }
                "legend" to {
                    "top" to "5%"
                    "left" to "center"
                }
                "series" toList {
                    item {
                        "name" to "Access From"
                        "type" to "pie"
                        "radius" to listOf("40%", "70%")
                        "avoidLabelOverlap" to false
                        "itemStyle" to {
                            "borderRadius" to 10
                            "borderColor" to "#FFF"
                            "borderWidth" to 2
                        }
                        "label" to {
                            "show" to false
                            "position" to "center"
                        }
                        "emphasis" to {
                            "label" to {
                                "show" to true
                                "fontSize" to 20
                                "fontWeight" to "bold"
                            }
                        }
                        "labelLine" to {
                            "show" to false
                        }
                        "data" toList {
                            items(pieDataList) {
                                "value" to it.value
                                "name" to it.name
                            }
                        }
                    }
                }
            }
        )
    }

    private fun createCandlestick() {
        _candlestick.value = replaceEchartsHtml(
            echarts {
                "xAxis" to {
                    "type" to "category"
                    "data" to listOf("2017-10-24", "2017-10-25", "2017-10-26", "2017-10-27")
                    "axisLabel" to {
                        "interval" to 0
                    }
                }
                "yAxis" to {}
                "series" toList {
                    item {
                        "type" to "candlestick"
                        "data" to listOf(
                            listOf(20, 34, 10, 38),
                            listOf(40, 35, 30, 50),
                            listOf(31, 38, 33, 44),
                            listOf(38, 15, 5, 42)
                        )
                    }
                }
            }
        )
    }

    private fun createRadar() {
        val radarIndicatorList = listOf(
            DemoModel("Sales", 6500),
            DemoModel("Administration", 16000),
            DemoModel("Information Technology", 30000),
            DemoModel("Customer Support", 38000),
            DemoModel("Development", 52000),
            DemoModel("Marketing", 25000)
        )
        _radar.value = replaceEchartsHtml(
            echarts {
                "title" to {
                    "text" to "Radar"
                }
                "legend" to {
                    "data" to listOf("Allocated Budget", "Actual Spending")
                    "left" to "right"
                }
                "radar" to {
                    "indicator" toList {
                        items(radarIndicatorList) {
                            "name" to it.name
                            "max" to it.value
                        }
                    }
                }
                "series" toList {
                    item {
                        "name" to "Budget vs spending"
                        "type" to "radar"
                        "data" toList {
                            item {
                                "value" to listOf(4200, 3000, 20000, 35000, 50000, 18000)
                                "name" to "Allocated Budget"
                            }
                            item {
                                "value" to listOf(5000, 14000, 28000, 26000, 42000, 21000)
                                "name" to "Actual Spending"
                            }
                        }
                    }
                }
            }
        )
    }

    private fun createTree() {
        val treeNode = TreeNode("flare", -1f)

        val treeNode1 = TreeNode("animate", 20.3f)
        val treeNode2 = TreeNode("display", 56f)

        val treeNode11 = TreeNode("Easing", 88.6f)
        val treeNode12 = TreeNode("Tween", 10f)
        val treeNode13 = TreeNode("Pause", 35.6f)
        val treeNode14 = TreeNode("Parallel", 46f)
        val treeNode15 = TreeNode("Scheduler", 30f)

        val treeNode21 = TreeNode("LineSprite", 72f)
        val treeNode22 = TreeNode("DirtySprite", 3f)

        treeNode1.children = listOf(treeNode11, treeNode12, treeNode13, treeNode14, treeNode15)
        treeNode2.children = listOf(treeNode21, treeNode22)

        treeNode.children = listOf(treeNode1, treeNode2)

        _tree.value = replaceEchartsHtml(
            echarts {
                "tooltip" to {
                    "trigger" to "item"
                    "triggerOn" to "click"
                    "confine" to true
                    "formatter" to EchartsFunction(
                        params = listOf("params"),
                        block = """
                            return params.data.name + ((params.data.value >= 0) ? ('  ' + params.data.value + '%') : '');
                        """.trimIndent()
                    )
                }
                "series" toList {
                    item {
                        "type" to "tree"
                        "top" to "middle"
                        "left" to "12%"
                        "right" to "20%"
                        "height" to "100%"
                        "symbolSize" to 20
                        "expandAndCollapse" to false
                        "data" toList {
                            item {
                                createNode(treeNode)
                            }
                        }
                        "label" to {
                            "position" to "left"
                            "verticalAlign" to "middle"
                            "align" to "right"
                            "color" to "#828282"
                        }
                        "leaves" to {
                            "label" to {
                                "position" to "right"
                                "verticalAlign" to "middle"
                                "align" to "left"
                            }
                        }
                    }
                }
            }
        )
    }

    private fun EchartsUnit.createNode(node: TreeNode) {
        "name" to node.name
        "value" to node.value
        node.children?.let {
            if (it.isNotEmpty()) {
                "children" toList {
                    items(it) { child ->
                        createNode(child)
                    }
                }
            }
        }
    }
}