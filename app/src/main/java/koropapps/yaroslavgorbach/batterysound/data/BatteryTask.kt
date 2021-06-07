package koropapps.yaroslavgorbach.batterysound.data

import androidx.annotation.Nullable
import org.w3c.dom.Text

data class BatteryTask(
    val id: Int,
    var level: Int,
    @Nullable var uri: String?,
    @Nullable var text: String?,
    var isActive: Boolean = false,
    var isConsumed: Boolean = false
)
