package koropapps.yaroslavgorbach.batterysound.screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.ItemTaskBinding

class TasksListAdapter(private val onTask: (BatteryTask) -> Unit) :
    RecyclerView.Adapter<TasksListAdapter.Vh>() {
    private var data: List<BatteryTask> = ArrayList()

    fun setData(items: List<BatteryTask>) {
        data = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false), onTask)

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }

    inner class Vh(
        private val binding: ItemTaskBinding,
        private val onTask: (BatteryTask) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.icPlay.setOnClickListener {
                onTask(data[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: BatteryTask) {
            binding.text.text = item.text
            binding.level.text = item.level.toString() + "%"

                binding.icPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        if (item.isActive) R.drawable.ic_stop else R.drawable.ic_play
                    )
                )
        }
    }
}