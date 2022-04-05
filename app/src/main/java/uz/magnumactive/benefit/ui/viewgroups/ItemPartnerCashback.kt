package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner_cashback.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants
import uz.magnumactive.benefit.util.loadImageUrl

class ItemPartnerCashback(val partner: Partner, val onClick: (partner: Partner) -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivIcon.loadImageUrl(partner.icon_image!!)
        viewHolder.itemView.tvBrand.text = partner.title
        viewHolder.itemView.tvCashBackPercentage.text = if(partner.type==0)
            partner.percent.toString() + "% " + viewHolder.itemView.context.getString(R.string.cashback) else viewHolder.itemView.context.getText(R.string.benefit_installment)
        viewHolder.itemView.tvCategoryName.text =
            if (AppPrefs.language == Constants.RU) partner.short_ru else if (AppPrefs.language == Constants.EN) partner.short_en else partner.short_uz

        viewHolder.itemView.clParent.setOnClickListener {
            onClick(partner)
        }

    }

    override fun getLayout() = R.layout.item_partner_cashback

}
