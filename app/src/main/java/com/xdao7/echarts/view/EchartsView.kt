package com.xdao7.echarts.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData

/**
 * PS：echarts.html当中使用的echarts.min.js为定制版
 * 只支持图表中的柱状图，折线图，饼图，K线图，雷达图，树图，坐标系中的直角坐标系，组件中的标题，图例和提示框
 * 如需其他组件，需前往官网（https://echarts.apache.org/zh/builder.html）定制更新echarts.min.js文件
 */
@Composable
fun EchartsView(
    modifier: Modifier,
    html: String
) {
    val state = rememberWebViewStateWithHTMLData(data = html, mimeType = "text/html")
    WebView(
        state = state,
        modifier = modifier,
        onCreated = {
            it.apply {
                settings.apply {
                    allowFileAccess = true
                    javaScriptEnabled = true
                }
                setOnLongClickListener { true }
            }
        }
    )
}