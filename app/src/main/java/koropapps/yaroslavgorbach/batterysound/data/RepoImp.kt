package koropapps.yaroslavgorbach.batterysound.data

class RepoImp : Repo {
    private val tasks = arrayListOf(
        BatteryTask(1,53, null, "TIME TO CHARGE", true),
        BatteryTask(2,52, null, "TIME TO CHARGE", true),
        BatteryTask(1,53, null, "TIME TO CHARGE", true),
        BatteryTask(1,54, null, "TIME TO CHARGE", true),
        BatteryTask(1,55, null, "TIME TO CHARGE", true),
        BatteryTask(1,56, null, "TIME TO CHARGE", true),
    )

    override fun getTask(id: Int): BatteryTask {
        return tasks[0]
    }

    override fun getTasks(): List<BatteryTask> {
        return tasks
    }
}