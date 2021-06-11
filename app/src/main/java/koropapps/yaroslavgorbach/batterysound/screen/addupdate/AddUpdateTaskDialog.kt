package koropapps.yaroslavgorbach.batterysound.screen.addupdate

import android.Manifest
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.DialogAddUpdateTaskBinding
import koropapps.yaroslavgorbach.batterysound.utill.getName
import kotlin.properties.Delegates

class AddUpdateTaskDialog : DialogFragment() {

    interface Host {
        fun onAdded(level: Int, text: String?, fileUri: Uri?)
        fun onUpdated(batteryTask: BatteryTask)
    }

    companion object {
        fun argsOf(task: BatteryTask) = bundleOf("task" to task)
        private val AddUpdateTaskDialog.task: BatteryTask? get() = arguments?.let { it["task"] as BatteryTask }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var v: AddUpdateTaskView? = null

        var fileUri: Uri? by Delegates.observable(null) { _, _, new ->
            v?.setFileName(new?.getName(requireContext()) ?: "File name")
        }

        val getUri = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            fileUri = uri
        }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    getUri.launch("audio/*")
                }
            }

        // init view
        v = AddUpdateTaskView(
            DialogAddUpdateTaskBinding.inflate(LayoutInflater.from(requireContext())),
            object : AddUpdateTaskView.Callback {
                override fun onPickFile() {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                override fun onAdd(level: Int, text: String?) {
                    (parentFragment as Host).onAdded(level, text, fileUri)
                    dialog?.dismiss()
                }

                override fun onUpdate(batteryTask: BatteryTask) {
                    fileUri?.let {
                        batteryTask.fileUri = it
                        batteryTask.text = null
                    }
                    (parentFragment as Host).onUpdated(batteryTask)
                    dialog?.dismiss()
                }
            })

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(v.getRoot())
            .setTitle(R.string.createTask)
            .create()

        // if task not null it means update
        task?.let {
            dialog.setTitle(R.string.updateTask)
            v.setTask(it)
        }

        return dialog
    }
}


