package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.ui.selected_partners_category.SelectedPartnersCategoryActivity
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner.view.*
import splitties.activities.start

class ItemPartnerCategory(val partner: PartnerCategoryDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivImage.loadImageUrl(partner.image)
        viewHolder.itemView.tvPartnerTitle.text = partner.title_ru
        viewHolder.itemView.cardCategory.setOnClickListener {
            viewHolder.itemView.context.start<SelectedPartnersCategoryActivity> {
                putExtra(SelectedPartnersCategoryActivity.EXTRA_CATEGORY, partner)
            }
        }

    }

    override fun getLayout() = R.layout.item_partner

}
