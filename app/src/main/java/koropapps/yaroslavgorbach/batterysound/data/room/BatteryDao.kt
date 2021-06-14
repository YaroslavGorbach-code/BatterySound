package koropapps.yaroslavgorbach.batterysound.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BatteryDao {

    @Insert
    suspend fun insert(batteryTask: BatteryTask)

    @Delete
    suspend fun delete(batteryTask: BatteryTask)

    @Update
    suspend fun update(batteryTask: BatteryTask)

    @Query("SELECT * FROM BatteryTask")
    fun getAllLive(): LiveData<List<BatteryTask>>

    @Query("SELECT * FROM BatteryTask")
    suspend fun getAll(): List<BatteryTask>

    @Query("SELECT * FROM BatteryTask WHERE isActive = 1 ")
    suspend fun getActive(): List<BatteryTask>
}