package koropapps.yaroslavgorbach.batterysound.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import java.util.*

data class BatteryTask(
    val id: Int,
    var level: Int,
    @Nullable var uri: String?,
    @Nullable var text: String?,
    var isActive: Boolean = false,
    var isConsumed: Boolean = false,
    var createDate: Long = Date().time
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(level)
        parcel.writeString(uri)
        parcel.writeString(text)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeByte(if (isConsumed) 1 else 0)
        parcel.writeLong(createDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BatteryTask> {
        override fun createFromParcel(parcel: Parcel): BatteryTask {
            return BatteryTask(parcel)
        }

        override fun newArray(size: Int): Array<BatteryTask?> {
            return arrayOfNulls(size)
        }
    }
}
