package com.xdao7.echarts.dsl

class EchartsUnit(val sb: StringBuilder) {

    infix fun String.to(value: Any) {
        sb.append("$this: ${getJsParameter(value)},")
    }

    infix fun String.to(value: EchartsUnit.() -> Unit) {
        sb.append("$this: {")
        value()
        sb.append("},")
    }

    infix fun String.toList(value: EchartsUnit.() -> Unit) {
        sb.append("$this: [")
        value()
        sb.append("],")
    }
}

class EchartsFunction(
    private val params: List<String>,
    private val block: String
) {

    override fun toString(): String {
        return "function (${params.joinToString()}) { $block }"
    }
}

fun getJsParameter(value: Any): String = when (value) {
    is Boolean, is Number, is EchartsFunction -> "$value"

    is String -> "'$value'"

    is List<*> -> {
        buildString {
            append("[")
            value.forEach { item ->
                item?.let {
                    append("${getJsParameter(it)},")
                }
            }
            append("]")
        }
    }

    else -> ""
}

inline fun echarts(action: EchartsUnit.() -> Unit) = buildString {
    append("{")
    EchartsUnit(this).action()
    append("}")
}

inline fun EchartsUnit.item(action: EchartsUnit.() -> Unit) {
    sb.append("{")
    action()
    sb.append("},")
}

inline fun <T> EchartsUnit.items(list: List<T>, action: EchartsUnit.(T) -> Unit) {
    list.forEach {
        sb.append("{")
        action(it)
        sb.append("},")
    }
}