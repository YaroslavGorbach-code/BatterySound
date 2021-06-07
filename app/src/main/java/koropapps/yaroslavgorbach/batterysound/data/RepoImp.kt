package koropapps.yaroslavgorbach.batterysound.data

class RepoImp : Repo {
    private val tasks = arrayListOf(
        BatteryTask(1,84, null, "БРАТ ВЖЕ ЇДЕ", true),
        BatteryTask(2,5, null, "НА ЗАРЯДКУ !!!", true))

    override fun getTask(id: Int): BatteryTask {
        return tasks[0]
    }

    override fun getTasks(): List<BatteryTask> {
        return tasks
    }
}