package es.alvarorodriguez.pruebafcm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.alvarorodriguez.pruebafcm.core.BaseViewHolder
import es.alvarorodriguez.pruebafcm.data.model.Cars
import es.alvarorodriguez.pruebafcm.databinding.CarItemViewBinding

class HomeAdapter(private val carList: List<Cars>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CarItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is HomeViewHolder -> holder.bind(carList[position])
        }
    }

    override fun getItemCount(): Int = carList.size

    private inner class HomeViewHolder(
        val binding: CarItemViewBinding
    ) : BaseViewHolder<Cars>(binding.root) {
        override fun bind(item: Cars) {
            binding.txtMarca.text = item.brand
            binding.txtDescription.text = item.description
        }
    }
}