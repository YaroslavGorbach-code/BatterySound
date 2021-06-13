package koropapps.yaroslavgorbach.batterysound.data

interface CommonPref {
    fun setDoNotDisturbStart(h: Int, m: Int)
    fun setDoNotDisturbEnd(h: Int, m: Int)
    fun getDoNotDisturbStartH(): Int
    fun getDoNotDisturbStartM(): Int
    fun getDoNotDisturbEndH(): Int
    fun getDoNotDisturbEndM(): Int
}