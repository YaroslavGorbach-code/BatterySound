package koropapps.yaroslavgorbach.batterysound.screen.createupdate

import android.Manifest
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.DialogCreateUpdateTaskBinding
import koropapps.yaroslavgorbach.batterysound.util.getName
import kotlin.properties.Delegates

class CreateUpdateTaskDialog : DialogFragment() {

    interface Host {
        fun onAdded(level: Int, text: String?, fileUri: Uri?)
        fun onUpdated(batteryTask: BatteryTask)
    }

    companion object {
        fun argsOf(task: BatteryTask) = bundleOf("task" to task)
        private val CreateUpdateTaskDialog.task: BatteryTask? get() = arguments?.let { it["task"] as BatteryTask }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var v: CreateUpdateTaskView? = null

        val getUri = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            v?.setUri(uri)
        }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    getUri.launch(arrayOf("audio/*"))
                }
            }

        // init view
        v = CreateUpdateTaskView(
            DialogCreateUpdateTaskBinding.inflate(LayoutInflater.from(requireContext())),
            object : CreateUpdateTaskView.Callback {
                override fun onPickFile() {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                override fun onAdd(level: Int, text: String?, fileUri: Uri?) {
                    (parentFragment as Host).onAdded(level, text, fileUri)
                    dialog?.dismiss()
                }

                override fun onUpdate(batteryTask: BatteryTask) {
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


