package com.xdao7.echarts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xdao7.echarts.view.EchartsView
import com.xdao7.echarts.ui.theme.XDEchartsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            XDEchartsTheme {
                EchartsDemo()
            }
        }
    }
}

@Composable
private fun EchartsDemo(
    model: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val line by model.line.collectAsStateWithLifecycle()
    val bar by model.bar.collectAsStateWithLifecycle()
    val pie by model.pie.collectAsStateWithLifecycle()
    val candlestick by model.candlestick.collectAsStateWithLifecycle()
    val radar by model.radar.collectAsStateWithLifecycle()
    val tree by model.tree.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        model.init(context)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                if (line.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        html = line
                    )
                }
            }
            item {
                if (bar.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        html = bar
                    )
                }
            }
            item {
                if (pie.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        html = pie
                    )
                }
            }
            item {
                if (candlestick.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        html = candlestick
                    )
                }
            }
            item {
                if (radar.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        html = radar
                    )
                }
            }
            item {
                if (tree.isNotEmpty()) {
                    EchartsView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        html = tree
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    XDEchartsTheme {
        EchartsDemo()
    }
}