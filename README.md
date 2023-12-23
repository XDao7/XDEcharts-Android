# XDEcharts
一个利用了Kotlin DSL特性（大概）编写的，低成本的在Android APP中展示Echarts图表的方案。

适用于后端不提供HTML文件，以及数据结构和Echarts Option不一致的情况。

![Image](https://github.com/XDao7/XDEcharts-Android/blob/master/docs/res/XDEcharts.jpg)

## 导入
直接将[EchartsDsl.kt](https://github.com/XDao7/XDEcharts-Android/blob/master/app/src/main/java/com/xdao7/echarts/dsl/EchartsDsl.kt)文件复制到你的工程，或者将其中的方法复制到你的工程即可。

## 使用
调用`echarts { ... }`创建一个作用域，方法本身返回拼接完成的js option对象，例如：
```
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
```
生成的结构为（换行和缩进为了可读性，本身生成的字符串无换行和缩进）：
```
{
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    },
    yAxis: {
        type: 'value',
    },
    series: [
        {
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: 'line',
            areaStyle: {},
        },
    ],
}
```
更多例子参考[MainViewModel.kt](https://github.com/XDao7/XDEcharts-Android/blob/master/app/src/main/java/com/xdao7/echarts/MainViewModel.kt)

## 语法
### A to B
`A to B`的语句会拼接一个形如`name: value,`结构的字符串，其中A必须是`Sting`类型，B则任意，但目前B只针对`Boolean`，`Number`，`String`，`List`，`EchartsFunction`做了相应处理。
其中`String`类型会额外增加单引号''，`List`类型会生成`[ ... ],`结构，EchartsFunction则会生成`function (params, ...) { ... },`结构

### A to {}
`A to {}`的语句同上，A必须是`Sting`类型，生成的字符串结构为：`name: { ... },`，例如：
```
"title" to { ... }
```
生成的结构如下：
```
title: { ... },
```

### A toList {}
`A toList {}`的语句同上，A必须是`Sting`类型，生成的字符串结构为：`name: [ ... ],`，例如：
```
"data" toList { ... }
```
生成的结构如下：
```
data: [ ... ],
```

### item {}
`item {}`会生成一个没有名称的大括号结构`{ ... },`

### items(List) {}
`items(List) {}`会在内部循环传入的List对象，为每一个对象生成一个没有名称的大括号结构`{ ... },`，例如：
```
val dataList = listOf(
    DemoModel("name1", 1048),
    DemoModel("name2", 735),
    DemoModel("name3", 580)
)
items(dataList) {
    "value" to it.value
    "name" to it.name
}
```
生成的结构如下：
```
{
    value: 1048,
    name: 'name1',
},
{
    value: 735,
    name: 'name2',
},
{
    value: 580,
    name: 'name3',
},
```

## 扩展
`echarts`方法的作用域中，随时可以使用`EchartsUnit`的`StringBuilder`对象，用于拼接你想要的任何结构的字符串。

也可以编写`EchartsUnit`的扩展函数来增加其他结构，例如你需要一个没有名称的中括号结构`[ ... ],`，你可以编写如下扩展函数：
```
inline fun EchartsUnit.array(action: EchartsUnit.() -> Unit) {
    sb.append("[")
    action()
    sb.append("],")
}
```

此外，可以在`getJsParameter`方法中添加额外的，自定义的类型的处理方式。
