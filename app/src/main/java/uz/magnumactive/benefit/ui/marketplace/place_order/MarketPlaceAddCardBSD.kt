//package uz.magnumactive.benefit.ui.marketplace.place_order
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import kotlinx.android.synthetic.main.bsd_cards.*
//import kotlinx.android.synthetic.main.item_card_pan.view.*
//import uz.magnumactive.benefit.R
//import uz.magnumactive.benefit.remote.models.CardDTO
//import uz.magnumactive.benefit.ui.main.BenefitBSD
//import uz.magnumactive.benefit.ui.main.home.bsd_add_card.AddCardBSD
//
//class MarketPlaceAddCardBSD(val cards: List<CardDTO>, val selectedCard: (CardDTO) -> Unit) : BenefitBSD() {
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = inflater.inflate(R.layout.bsd_cards, null)
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupView()
//        attachListeners()
//    }
//
//    private fun setupView() {
//
//        llContainer.removeAllViews()
//        cards.forEach {
//            val cardPanView = layoutInflater.inflate(R.layout.item_card_pan, null)
//            cardPanView.rbCardPan.text = it.panHidden
//            if (it.isChecked) cardPanView.rbCardPan.isChecked = true
//            cardPanView.rbCardPan.setOnCheckedChangeListener { buttonView, isChecked ->
//                it.isChecked = true
//                selectedCard(it)
//                dismiss()
//            }
//            llContainer.addView(cardPanView)
//        }
//    }
//
//    private fun attachListeners() {
//
//        tvAddCard.setOnClickListener {
//            val dialog = AddCardBSD()
//            dialog.show(childFragmentManager, "")
//        }
//
//
//    }
//
//
//}
