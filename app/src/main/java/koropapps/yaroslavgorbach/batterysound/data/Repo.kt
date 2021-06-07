package koropapps.yaroslavgorbach.batterysound.data

interface Repo {
    fun getTask(id: Int): BatteryTask
}