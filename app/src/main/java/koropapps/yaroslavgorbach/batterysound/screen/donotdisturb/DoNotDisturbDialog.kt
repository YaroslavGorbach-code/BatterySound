package koropapps.yaroslavgorbach.batterysound.screen.donotdisturb

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.databinding.DialogDoNotDisturbBinding

class DoNotDisturbDialog : DialogFragment() {

    interface Host {
        fun onStartTimeChanged(h: Int, m: Int)
        fun onEndTimeChanged(h: Int, m: Int)
    }

    companion object {
        fun argsOf(startH: Int, startM: Int, endH: Int, endM: Int) =
            bundleOf("startH" to startH, "startM" to startM, "endH" to endH, "endM" to endM)

        private val DoNotDisturbDialog.startH: Int? get() = arguments?.let { it["startH"] as Int? }
        private val DoNotDisturbDialog.startM: Int? get() = arguments?.let { it["startM"] as Int? }
        private val DoNotDisturbDialog.endH: Int? get() = arguments?.let { it["endH"] as Int? }
        private val DoNotDisturbDialog.endM: Int? get() = arguments?.let { it["endM"] as Int? }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = DoNotDisturbView(
            DialogDoNotDisturbBinding.inflate(LayoutInflater.from(requireContext())),
            object : DoNotDisturbView.Callback {
                override fun onStartChanged(h: Int, m: Int) {
                    (parentFragment as Host).onStartTimeChanged(h, m)
                }

                override fun onEndChanged(h: Int, m: Int) {
                    (parentFragment as Host).onEndTimeChanged(h, m)
                }
            })

        v.setStartTime(startH, startM)
        v.setEndTime(endH, endM)
        return MaterialAlertDialogBuilder(requireContext())
            .setView(v.getRoot())
            .setTitle(R.string.do_not_disturb)
            .create()
    }
}