package koropapps.yaroslavgorbach.batterysound.screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.DialogAddTaskBinding
import koropapps.yaroslavgorbach.batterysound.utill.filterIsEmpty
import koropapps.yaroslavgorbach.batterysound.utill.filterLevel

class AddUpdateTaskDialog : DialogFragment() {

    interface Host {
        fun onAdded(level: Int, text: String)
        fun onUpdated(batteryTask: BatteryTask)
    }

    companion object {
        fun argsOf(task: BatteryTask) = bundleOf("task" to task)
        private val AddUpdateTaskDialog.task: BatteryTask? get() = arguments?.let { it["task"] as BatteryTask }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bind = DialogAddTaskBinding.inflate(LayoutInflater.from(context))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bind.root)
            .setTitle(R.string.createTask)
            .create()

        // if task not null it means update
        task?.let {
            bind.level.setText(task!!.batteryLevel.toString())
            bind.text.setText(task!!.text.toString())
            dialog.setTitle(R.string.updateTask)
            bind.add.text = getString(R.string.update)
        }

        bind.add.setOnClickListener {
            // if task not null it means update
            if (bind.level.filterIsEmpty() && bind.level.filterLevel()) {
                if (task != null) {
                    task!!.batteryLevel = bind.level.text.toString().toInt()
                    task!!.text = bind.text.text.toString()
                    (parentFragment as Host).onUpdated(task!!)
                } else {
                    (parentFragment as Host).onAdded(
                        bind.level.text.toString().toInt(),
                        bind.text.text.toString()
                    )
                }
                dialog.dismiss()
            }
        }
        return dialog
    }
}


