package koropapps.yaroslavgorbach.batterysound.data

class RepoImp : Repo {
    private val testTask = BatteryTask(1,84, null, null, true)
    override fun getTask(id: Int): BatteryTask {
        return testTask;
    }
}