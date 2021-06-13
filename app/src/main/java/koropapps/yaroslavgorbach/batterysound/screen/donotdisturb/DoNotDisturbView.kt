package koropapps.yaroslavgorbach.batterysound.screen.donotdisturb

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import koropapps.yaroslavgorbach.batterysound.databinding.DialogDoNotDisturbBinding
import koropapps.yaroslavgorbach.batterysound.util.convertHourAndMinuteToString
import java.util.*

@SuppressLint("SetTextI18n")
class DoNotDisturbView(
    private val binding: DialogDoNotDisturbBinding,
    private val callback: Callback
) {

    interface Callback {
        fun onStartChanged(h: Int, m: Int)
        fun onEndChanged(h: Int, m: Int)
    }

    init {
        binding.start.setOnClickListener {
            TimePickerDialog(
                binding.root.context,
                { _: TimePicker?, h: Int, m: Int ->
                    binding.startTime.text = convertHourAndMinuteToString(
                        h,
                        m,
                        DateFormat.is24HourFormat(binding.root.context)
                    )
                    callback.onStartChanged(h, m)
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(binding.root.context)
            ).show()
        }

        binding.end.setOnClickListener {
            TimePickerDialog(
                binding.root.context,
                { _: TimePicker?, h: Int, m: Int ->

                    binding.endTime.text = convertHourAndMinuteToString(
                        h,
                        m,
                        DateFormat.is24HourFormat(binding.root.context)
                    )
                    callback.onEndChanged(h, m)
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(binding.root.context)
            ).show()
        }
    }

    fun getRoot() = binding.root

    fun setStartTime(h: Int?, m: Int?) {
        if (h != null && m != null)
            binding.startTime.text =
                convertHourAndMinuteToString(h, m, DateFormat.is24HourFormat(binding.root.context))
    }

    fun setEndTime(h: Int?, m: Int?) {
        if (h != null && m != null)
            binding.endTime.text =
                convertHourAndMinuteToString(h, m, DateFormat.is24HourFormat(binding.root.context))
    }

}