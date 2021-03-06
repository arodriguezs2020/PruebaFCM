package es.alvarorodriguez.pruebafcm.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.alvarorodriguez.pruebafcm.core.BaseViewHolder
import es.alvarorodriguez.pruebafcm.data.model.Cars
import es.alvarorodriguez.pruebafcm.databinding.CarItemBinding

class HomeAdapter(private val carList: List<Cars>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is HomeViewHolder -> holder.bind(carList[position])
        }
    }

    override fun getItemCount(): Int = carList.size

    private inner class HomeViewHolder(
        val binding: CarItemBinding,
        val context: Context
    ) : BaseViewHolder<Cars>(binding.root) {
        override fun bind(item: Cars) {
            binding.marca.text = item.brand
            binding.description.text = item.description
            Glide.with(context).load(item.car_image).centerCrop().into(binding.imgCar)
        }
    }
}