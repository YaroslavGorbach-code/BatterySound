package koropapps.yaroslavgorbach.batterysound.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import java.util.*

data class BatteryTask(
    val id: Int,
    var batteryLevel: Int,
    @Nullable var fileUri: Uri?,
    @Nullable var text: String?,
    var isActive: Boolean = false,
    var isConsumed: Boolean = false,
    var created: Long = Date().time
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(batteryLevel)
        parcel.writeParcelable(fileUri, flags)
        parcel.writeString(text)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeByte(if (isConsumed) 1 else 0)
        parcel.writeLong(created)
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
