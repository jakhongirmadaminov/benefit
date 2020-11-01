package com.example.benefit.ui.main.home.card_options

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.CardBgDTO
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.bsd_add_card.AddCardBSD
import com.example.benefit.ui.viewgroups.ItemCardDesign
import com.example.benefit.util.loadImageUrl
import com.rd.utils.DensityUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_card_change_design.*
import kotlinx.android.synthetic.main.item_card.view.*
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CardChangeDesignFragment @Inject constructor() :
    Fragment(R.layout.fragment_card_change_design) {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: CardOptionsViewModel by viewModels()
    val args: CardChangeDesignFragmentArgs by navArgs()


//    lateinit var card: CardDTO

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        card = requireArguments().getParcelable(FillCardFragment.ARG_CARD)!!
//        cards = requireArguments().getParcelableArrayList(FillCardFragment.ARG_CARDS)!!
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getCardDesigns()
    }

    private fun setupViews() {
        rvDesigns.adapter = adapter
        setupCardsPager(args.cards!!)


    }


    private fun subscribeObservers() {

        viewModel.cardBgs.observe(viewLifecycleOwner, {
            val resp = it ?: return@observe
            loadData(resp)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            if (result) {
                progress.visibility = View.VISIBLE
                cardsPager.visibility = View.INVISIBLE
            } else {
                progress.visibility = View.GONE
                cardsPager.visibility = View.VISIBLE
            }
        })

    }

    private fun loadData(resp: List<CardBgDTO>) {

        adapter.clear()
        resp.forEach {
            adapter.add(ItemCardDesign(it) {

            })
        }

    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun setupCardsPager(cardsDTO: List<CardDTO>) {
        val cardViews = arrayListOf<View>()
        cardsDTO.forEach {
            val cardView = makeCardView(it)
            cardsPager.addView(cardView)
            cardViews.add(cardView)
        }
        cardsPager.adapter = HomeFragment.WizardPagerAdapter(cardViews)
        cardsPager.offscreenPageLimit = cardViews.size
        cardsPager.clipToPadding = false
        cardsPager.setPadding(DensityUtils.dpToPx(26), 0, DensityUtils.dpToPx(26), 0)
        cardsPager.pageMargin = DensityUtils.dpToPx(15)
    }

    private fun makeCardView(cardDTO: CardDTO): View {
        val view = layoutInflater.inflate(R.layout.item_card, cardsPager, false)
        view.tvCardOwner.text = cardDTO.fullName
        view.tvCardNumber.text = cardDTO.pan
        view.tvCardName.text = cardDTO.card_title
        if (cardDTO.background_link != null) view.cardBg.loadImageUrl(cardDTO.background_link)
        view.tvBalance.text = "${DecimalFormat("#,###").format(cardDTO.balance!!.toInt())} UZS"
        return view
    }


}