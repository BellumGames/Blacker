package com.bellum.blacker.main

import com.bellum.blacker.MainActivity
import java.io.File
import java.text.AttributedString

fun readFromFile(fileName: String): String {
    var data:String = ""
    val lines: List<String> = File(fileName).readLines()
    for (line in lines)
        data += line

    return data
}