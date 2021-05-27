package com.example.benefit.ui.main.home.card_options

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.benefit.R
import com.example.benefit.remote.models.CardBgDTO
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.viewgroups.ItemCardDesign
import com.example.benefit.util.loadImageUrl
import com.google.android.material.snackbar.Snackbar
import com.rd.utils.DensityUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_card_change_design.*
import kotlinx.android.synthetic.main.item_card.view.*
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class CardChangeDesignFragment @Inject constructor() :
    Fragment(R.layout.fragment_card_change_design) {


    var cardViews = ArrayList<View>()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: CardOptionsViewModel by viewModels()
    val args: CardChangeDesignFragmentArgs by navArgs()
    var selectedDesign: Int = 0
        set(value) {
            tvDone.isEnabled = value > 0
            field = value
        }

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
        setupCardsPager()


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

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            Snackbar.make(clParent, result, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun loadData(resp: List<CardBgDTO>) {

        adapter.clear()
        resp.forEach { cardBg ->
            adapter.add(ItemCardDesign(cardBg) {
                ((cardsPager.getChildAt(cardsPager.currentItem) as CardView).getChildAt(0) as ImageView).loadImageUrl(
                    cardBg.image
                )
                selectedDesign = cardBg.id
            })
        }

    }


    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tvDone.setOnClickListener {
            viewModel.setCardDesign(selectedDesign, args.cards!![cardsPager.currentItem].id!!)
        }

        cardsPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                resetCardBgs()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }

    private fun resetCardBgs() {
        args.cards!!.forEachIndexed { index, cardDTO ->
            ((cardViews[index] as CardView).getChildAt(0) as ImageView).loadImageUrl(
                cardDTO.background_link!!
            )
        }
        selectedDesign = 0
    }


    private fun setupCardsPager() {
        cardViews = arrayListOf()
        var selectedCardIndex = -1
        args.cards!!.forEachIndexed { index, cardDTO ->
            val cardView = makeCardView(cardDTO)
            cardsPager.addView(cardView)
            cardViews.add(cardView)
            if (cardDTO.id == args.card!!.id) {
                selectedCardIndex = index
            }
        }
        cardsPager.adapter = HomeFragment.WizardPagerAdapter(cardViews)
        cardsPager.offscreenPageLimit = cardViews.size
        cardsPager.clipToPadding = false
        cardsPager.setPadding(DensityUtils.dpToPx(26), 0, DensityUtils.dpToPx(26), 0)
        cardsPager.pageMargin = DensityUtils.dpToPx(15)
        cardsPager.currentItem = selectedCardIndex
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