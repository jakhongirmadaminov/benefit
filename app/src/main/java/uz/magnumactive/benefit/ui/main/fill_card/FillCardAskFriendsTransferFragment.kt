package uz.magnumactive.benefit.ui.main.fill_card

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.FriendDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.main.home.HomeFragment
import uz.magnumactive.benefit.ui.main.home.card_options.CardOptionsBSD
import uz.magnumactive.benefit.ui.main.home.card_options.CardOptionsViewModel
import uz.magnumactive.benefit.ui.viewgroups.ContactItemSquare
import uz.magnumactive.benefit.util.SizeUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_fill_card_ask_friends_transfer.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class FillCardAskFriendsTransferFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_fill_card_ask_friends_transfer) {

    companion object {
        const val ARG_SELECTION = "SELECTION"
    }

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private var selection: ArrayList<FriendDTO>? = null
    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        selection = arguments?.getParcelableArrayList(ARG_SELECTION)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        edtSum.doOnTextChanged { text, start, before, count ->
            tvMinAmount.isVisible =
                if (text.isNullOrBlank()) false else text.toString().toInt() < 1000
        }
    }


    private fun setupViews() {
        calcLayout.edtSum = edtSum
        calcLayout.footerTextView = tvMinAmount
        setupSelectedContacts()
        setupCardPager()

    }

    private fun setupSelectedContacts() {
        rvContacts.adapter = adapter
        adapter.clear()
        selection?.forEach {
            adapter.add(ContactItemSquare(it))
        }

        adapter.add(ContactItemSquare(null))

        adapter.notifyDataSetChanged()

    }

    private fun setupCardPager() {
        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsToPagerSmall.addView(cardView)
        cardsToPagerSmall.addView(cardView2)

        cardsToPagerSmall.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsToPagerSmall.offscreenPageLimit = 10
        cardsToPagerSmall.clipToPadding = false
        cardsToPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsToPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

    }

}