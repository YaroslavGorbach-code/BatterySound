package koropapps.yaroslavgorbach.batterysound.data.room

import android.content.Context
import android.net.Uri
import androidx.room.*

@Database(entities = [BatteryTask::class], version = 1)
@TypeConverters(Db.Converters::class)

abstract class Db : RoomDatabase() {
    abstract fun batteryDao(): BatteryDao

    companion object {
        fun getInstance(context: Context): Db {
            return Room.databaseBuilder(
                context,
                Db::class.java, "db"
            ).build()
        }
    }

    class Converters {
        @TypeConverter
        fun fromString(value: String?): Uri? {
            return if (value == null) null else Uri.parse(value)
        }

        @TypeConverter
        fun toString(uri: Uri?): String? {
            return uri?.toString()
        }
    }

}
