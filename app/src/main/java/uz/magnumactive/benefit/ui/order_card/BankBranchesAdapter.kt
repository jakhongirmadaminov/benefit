package uz.magnumactive.benefit.ui.order_card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BankBranchDTO
import kotlinx.android.synthetic.main.item_branch_name.view.*

class BankBranchesAdapter(val onClick: (bank: BankBranchDTO) -> Unit) :
    ListAdapter<BankBranchDTO, BankBranchesAdapter.BranchViewHolder>(BankBranchDiffCallback),
    Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_branch_name, parent, false)

        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }


    class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bank: BankBranchDTO, onClick: (bank: BankBranchDTO) -> Unit) {
            itemView.tvBranchName.text = bank.title_ru
            itemView.tvBranchName.setOnClickListener { onClick(bank) }
        }
    }


    var completeList = listOf<BankBranchDTO>()

    override fun submitList(list: List<BankBranchDTO>?) {
        super.submitList(list)
        if (completeList.isEmpty() && list?.size != 0) {
            completeList = list!!
        }
    }


    private var mFilteredList: List<BankBranchDTO> = listOf()
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    mFilteredList = completeList!!
                } else {

                    val filteredList = arrayListOf<BankBranchDTO>()
                    for (baseDataItem in completeList) {
                        if (charString.toLowerCase() in baseDataItem.title_ru!!.toLowerCase()) {
                            filteredList.add(baseDataItem)
                        }
                    }

                    mFilteredList = filteredList

                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                mFilteredList = filterResults.values as List<BankBranchDTO>
                submitList(mFilteredList.toList())
            }
        }
    }

}

object BankBranchDiffCallback : DiffUtil.ItemCallback<BankBranchDTO>() {
    override fun areItemsTheSame(oldItem: BankBranchDTO, newItem: BankBranchDTO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BankBranchDTO, newItem: BankBranchDTO): Boolean {
        return oldItem.region_id == newItem.region_id
    }
}

