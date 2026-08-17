package com.example.examengapsi.utils

fun Double.toPrice(): String {
    return "$${"%.2f".format(this)}"
}