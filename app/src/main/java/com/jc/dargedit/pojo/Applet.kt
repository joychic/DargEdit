package com.jc.dargedit.pojo

data class Applet(
    val name: String,
    val icon: String,
    var hide: Boolean = false // 是否要隐藏的标记
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Applet

        if (name != other.name) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }
}