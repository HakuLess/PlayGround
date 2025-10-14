package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


class ComposeDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoApp()
        }
    }
}

@Composable
fun ComposeDemoApp() {
    // 使用Material主题
    MaterialTheme {
        // 主要内容区域
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // 标题
            Text(
                text = "Compose UI 演示",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // 状态管理示例
            CounterExample()
            Spacer(modifier = Modifier.height(32.dp))
            
            // 文本输入示例
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("请输入文本") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            // 按钮示例
            Button(
                onClick = { /* 按钮点击事件 */ },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Compose按钮")
            }
            
            // 卡片示例
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("这是一个Compose卡片组件")
                }
            }
        }
    }
}

// 计数器示例
@Composable
fun CounterExample() {
    var count by remember { mutableStateOf(0) }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        Button(onClick = { count-- }) {
            Text("-")
        }
        Text(
                text = count.toString(),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.h5
            )
        Button(onClick = { count++ }) {
            Text("+")
        }
    }
}

// 文本输入示例
@Composable
fun TextInputExample() {
    var text by remember { mutableStateOf("") }
    
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("请输入内容") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

// 预览函数
@Preview(showBackground = true)
@Composable
fun PreviewComposeDemoApp() {
    ComposeDemoApp()
}