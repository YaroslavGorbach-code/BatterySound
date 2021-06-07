package koropapps.yaroslavgorbach.batterysound.data

interface Repo {
    fun getTask(id: Int): BatteryTask
    fun getTasks(): List<BatteryTask>
}