package koropapps.yaroslavgorbach.batterysound.data

import androidx.annotation.Nullable
import org.w3c.dom.Text
import java.util.*

data class BatteryTask(
    val id: Int,
    var level: Int,
    @Nullable var uri: String?,
    @Nullable var text: String?,
    var isActive: Boolean = false,
    var isConsumed: Boolean = false,
    var createDate: Long = Date().time
)
