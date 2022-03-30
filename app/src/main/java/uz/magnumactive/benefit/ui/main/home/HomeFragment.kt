package uz.magnumactive.benefit.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.models.EPaymentType
import uz.magnumactive.benefit.remote.models.LoanBody
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.stories.data.StoryUser
import uz.magnumactive.benefit.stories.screen.EXTRA_STORIES
import uz.magnumactive.benefit.stories.screen.StoryActivity
import uz.magnumactive.benefit.ui.auth.AuthActivity
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.branches_atms.BranchesAtmsActivity
import uz.magnumactive.benefit.ui.expenses_by_categories.ExpensesByCategoriesActivity
import uz.magnumactive.benefit.ui.loans.LoanActivity
import uz.magnumactive.benefit.ui.loans.LoansViewModel
import uz.magnumactive.benefit.ui.loans.loans_chart.EXTRA_LOAN_INFO
import uz.magnumactive.benefit.ui.loans.loans_chart.LoansChartActivity
import uz.magnumactive.benefit.ui.main.fill_card.FillCardBSD
import uz.magnumactive.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARD
import uz.magnumactive.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARDS
import uz.magnumactive.benefit.ui.main.home.bsd_add_card.AddCardBSD
import uz.magnumactive.benefit.ui.main.home.card_options.CardOptionsBSD
import uz.magnumactive.benefit.ui.main.transfer_to_card.TransferToCardBSD
import uz.magnumactive.benefit.ui.payments.ARG_PAYNET_CATEGORY
import uz.magnumactive.benefit.ui.payments.PaymentsBSD
import uz.magnumactive.benefit.ui.transactions_history.TransactionsHistoryActivity
import uz.magnumactive.benefit.ui.transactions_history.TransactionsHistoryActivity.Companion.EXTRA_CARD
import uz.magnumactive.benefit.ui.viewgroups.ItemLoading
import uz.magnumactive.benefit.ui.viewgroups.ItemPaynetCatg
import uz.magnumactive.benefit.ui.viewgroups.ItemStory
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import com.rd.utils.DensityUtils.dpToPx
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_action_card_one.view.*
import kotlinx.android.synthetic.main.item_action_card_three.view.*
import kotlinx.android.synthetic.main.item_action_card_two.view.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import splitties.fragments.start
import splitties.preferences.edit
import java.text.DecimalFormat


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val paynetCatgAdapter = GroupAdapter<GroupieViewHolder>()
    private val newsAdapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: HomeViewModel by viewModels()
    val loansViewModel: LoansViewModel by viewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPaynetCategories()
        viewModel.getStories()
        viewModel.getMyCards()
        setupActionCards()

    }

    private fun subscribeObservers() {

        loansViewModel.respLoanInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResultError -> {
                    setupActionCards()
                }
                is ResultSuccess -> {
                    setupActionCards(response.value.responseBody)
                }
            }
        }

        loansViewModel.isLoading.observe(viewLifecycleOwner) {
            progressActionCards.isVisible = it
        }

        viewModel.isLoadingPaynetCategories.observe(viewLifecycleOwner) {
            when (it ?: return@observe) {
                true -> paynetCatgAdapter.add(ItemLoading())
                else -> paynetCatgAdapter.clear()
            }
        }
        viewModel.signInRequired.observe(viewLifecycleOwner) {
            when (it ?: return@observe) {
                true -> {
                    val dialog = DialogAuthNeeded()
                    childFragmentManager.setFragmentResultListener(
                        KEY_NEED_AUTHORIZATION,
                        viewLifecycleOwner
                    ) { requestKey, result ->
                        AppPrefs.edit {
                            token = null
                        }
                        start<AuthActivity>()
                        requireActivity().finish()
                    }
                    dialog.show(childFragmentManager, "")
                }
                else -> {
                }
            }
        }
        viewModel.isLoadingStories.observe(viewLifecycleOwner) {
            when (it ?: return@observe) {
                true -> newsAdapter.add(ItemLoading())
                else -> newsAdapter.clear()
            }
        }
        viewModel.isLoadingCards.observe(viewLifecycleOwner) {
            when (it ?: return@observe) {
                true -> {
                    cardsProgress.visibility = View.VISIBLE
                    cardsPager.visibility = View.INVISIBLE
                }
                else -> {
                    cardsProgress.visibility = View.INVISIBLE
                    cardsPager.visibility = View.VISIBLE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
//            lblYoullReceiveCode.text = it ?: return@observe
//            lblYoullReceiveCode.setTextColor(Color.RED)
        }

        viewModel.paynetCatgResp.observe(viewLifecycleOwner) {
            it ?: return@observe
            paynetCatgAdapter.add(
                ItemPaynetCatg(
                    PaynetCategory(
                        paymentTypeEnum = EPaymentType.CARD_TRANSFER,
                        imageResource = R.drawable.ic_payment_card,
                        titleRu = getString(R.string.transfer_to_card)
                    )
                ) {
                    if (!viewModel.cardsResp.value.isNullOrEmpty()) {
                        TransferToCardBSD().show(childFragmentManager, "")
                    } else {
                        val dialog = DialogPleaseAddCard()
                        childFragmentManager.setFragmentResultListener(
                            KEY_ADD_CARD,
                            viewLifecycleOwner
                        ) { requestKey, result ->
                            AddCardBSD().show(childFragmentManager, "")
                        }
                        dialog.show(childFragmentManager, "")
                    }
                }
            )
            it.forEach { paynetCategory ->
                paynetCatgAdapter.add(ItemPaynetCatg(paynetCategory) { paynetCatg ->
                    if (!viewModel.cardsResp.value.isNullOrEmpty()) {
                        val paymentsDialog = PaymentsBSD()
                        paymentsDialog.arguments = Bundle().apply {
                            putParcelable(ARG_PAYNET_CATEGORY, paynetCatg)
                        }
                        paymentsDialog.show(childFragmentManager, "")
                    } else {
                        val dialog = DialogPleaseAddCard()
                        childFragmentManager.setFragmentResultListener(
                            KEY_ADD_CARD,
                            viewLifecycleOwner
                        ) { requestKey, result ->
                            AddCardBSD().show(childFragmentManager, "")
                        }
                        dialog.show(childFragmentManager, "")
                    }
                })
            }
        }

        viewModel.storiesResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe

            when (resp) {
                is ResultError -> {
                }
                is ResultSuccess -> {
                    val storyUsers =
                        ArrayList(resp.value.groupBy { it.partnerId }.values.map { stories ->
                            StoryUser(
                                stories[0].partnerTitle!!,
                                stories[0].partnerIconImage!!,
                                ArrayList(stories)
                            )
                        })
                    storyUsers.forEachIndexed { index, storyUser ->
                        val storiesToShow = arrayListOf<StoryUser>()
                        for (i in index until storyUsers.size) {
                            storiesToShow.add(storyUsers[i])
                        }
                        newsAdapter.add(
                            ItemStory(storyUser) {
                                startActivity(
                                    Intent(requireActivity(), StoryActivity::class.java).apply {
                                        putParcelableArrayListExtra(EXTRA_STORIES, storiesToShow)
                                    })
                            })
                    }
                }
            }

        }
        viewModel.cardsResp.observe(viewLifecycleOwner) {
            it ?: return@observe
            setupCardsPager(it)
        }

        viewModel.supremeCard.observe(viewLifecycleOwner) { supremeCard ->
            supremeCard?.let {
                loansViewModel.getLoanIdByPan(it.panOpen!!)
            } ?: run {
                setupActionCards()
            }
        }

    }

    private fun setupActionCards(loan: LoanBody? = null) {
        servicesPager.offscreenPageLimit = 10
        servicesPager.clipToPadding = false
        servicesPager.setPadding(dpToPx(26), 0, dpToPx(26), 0)
        servicesPager.pageMargin = dpToPx(15)
        servicesPager.adapter = null
        servicesPager.removeAllViews()
        val actionViews = arrayListOf<View>()
        val cardOne = layoutInflater.inflate(R.layout.item_action_card_one, null)
        cardOne.setOnClickListener {
            val dialog = DialogCashBack()
            childFragmentManager.setFragmentResultListener(
                KEY_GO_TO_LIST,
                viewLifecycleOwner
            ) { requestKey, result ->
                PaymentsBSD().show(childFragmentManager, "")
            }
            dialog.show(childFragmentManager, "")
        }
        cardOne.cardOvalCashback.setBackgroundResource(R.drawable.shape_oval_yellow)

        viewModel.getBftBalance().onEach { bftAmount ->
            cardOne.tvBFTAmount.text = (bftAmount?.toString() ?: "") + " BFT"
        }.launchIn(lifecycleScope)
        actionViews.add(cardOne)
        servicesPager.addView(cardOne)

        loan?.let {
            val cardTwo = layoutInflater.inflate(R.layout.item_action_card_two, null)
            cardTwo.setOnClickListener {
                start<LoansChartActivity> {
                    putExtra(EXTRA_LOAN_INFO, loan)
                    putExtra(EXTRA_CARD, viewModel.supremeCard.value)
                }
            }
            cardTwo.tvLoanSum.text = DecimalFormat("#,###").format(it.perCurr!!) + " UZS"
            cardTwo.cardOvalLoans.setBackgroundResource(R.drawable.shape_oval_yellow)
            actionViews.add(cardTwo)
            servicesPager.addView(cardTwo)
        }


        val cardThree = layoutInflater.inflate(R.layout.item_action_card_three, null)
        cardThree.setOnClickListener {

        }
        cardThree.cardOvalMessage.setBackgroundResource(R.drawable.shape_oval_yellow)
        actionViews.add(cardThree)
        servicesPager.addView(cardThree)
        servicesPager.adapter = WizardPagerAdapter(actionViews)
    }

    private fun attachListeners() {
        cardBranches.setOnClickListener {
            startActivity(Intent(requireActivity(), BranchesAtmsActivity::class.java))
        }


        cardExpenses.setOnClickListener {
            if (!viewModel.cardsResp.value.isNullOrEmpty()) {
                startActivity(
                    Intent(
                        requireActivity(),
                        ExpensesByCategoriesActivity::class.java
                    ).apply {
                        putParcelableArrayListExtra(
                            ARG_CARDS,
                            ArrayList(viewModel.cardsResp.value!!)
                        )
                    })
            } else {
                val dialog = DialogPleaseAddCard()
                childFragmentManager.setFragmentResultListener(
                    KEY_ADD_CARD,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    AddCardBSD().show(childFragmentManager, "")
                }
                dialog.show(childFragmentManager, "")
            }
        }

        cardPayments.setOnClickListener {
            if (!viewModel.cardsResp.value.isNullOrEmpty()) {
                startActivity(
                    Intent(
                        requireActivity(),
                        TransactionsHistoryActivity::class.java
                    ).apply {
                        putParcelableArrayListExtra(
                            ARG_CARDS,
                            ArrayList(viewModel.cardsResp.value!!)
                        )
                    })
            } else {
                val dialog = DialogPleaseAddCard()
                childFragmentManager.setFragmentResultListener(
                    KEY_ADD_CARD,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    AddCardBSD().show(childFragmentManager, "")
                }
                dialog.show(childFragmentManager, "")
            }
        }


        cardLimits.setOnClickListener {

            viewModel.supremeCard.value?.let { supremeCard ->
                startActivity(
                    Intent(requireActivity(), LoanActivity::class.java).apply {
                        putExtra(EXTRA_CARD, supremeCard)
                    })
            } ?: run {
                val dialog = DialogYouHaveNoSupremeCard()
                dialog.show(childFragmentManager, "")
            }
        }


    }

    private fun setupViews() {

        rvPayments.adapter = paynetCatgAdapter
        rvStories.adapter = newsAdapter
        cardOval.setBackgroundResource(R.drawable.shape_oval)
        cardOvalExpenses.setBackgroundResource(R.drawable.shape_oval)
        cardOvalBranches.setBackgroundResource(R.drawable.shape_oval)
        cardOvalLimits.setBackgroundResource(R.drawable.shape_oval)

    }

    private fun setupCardsPager(cardsDTO: List<CardDTO>) {
        val cardViews = arrayListOf<View>()
        cardsDTO.forEach {
            val cardView = makeCardView(it)
            cardsPager.addView(cardView)
            cardViews.add(cardView)
        }

        val cardView2 = layoutInflater.inflate(R.layout.item_add_card, cardsPager, false)
        cardView2.cardParent.setOnClickListener { AddCardBSD().show(childFragmentManager, "") }
        cardsPager.addView(cardView2)
        cardViews.add(cardView2)

        cardsPager.adapter = WizardPagerAdapter(cardViews)
        cardsPager.offscreenPageLimit = cardViews.size
        cardsPager.clipToPadding = false
        cardsPager.setPadding(dpToPx(26), 0, dpToPx(26), 0)
        cardsPager.pageMargin = dpToPx(15)
    }

    private fun makeCardView(cardDTO: CardDTO): View {
        val view = layoutInflater.inflate(R.layout.item_card, cardsPager, false)
        view.tvCardOwner.text = cardDTO.fullName
        view.tvCardNumber.text = cardDTO.panHidden
        view.tvCardName.text = cardDTO.card_title
        cardDTO.setBackgroundInto(view.cardBg, view.tvCardType)
        view.tvBalance.text =
            "${DecimalFormat("#,###").format(cardDTO.balance!!.dropLast(2).toInt())} UZS"
        view.icPlus.setOnClickListener {
            val dialog = FillCardBSD()
            dialog.arguments = Bundle().apply {
                putParcelable(ARG_CARD, cardDTO)
                putParcelableArrayList(ARG_CARDS, ArrayList(viewModel.cardsResp.value!!))
            }
            dialog.show(childFragmentManager, "")
        }
        view.setOnClickListener {
            val dialog = CardOptionsBSD()
            dialog.arguments = Bundle().apply {
                putParcelable(ARG_CARD, cardDTO)
                putParcelableArrayList(ARG_CARDS, ArrayList(viewModel.cardsResp.value!!))
            }
            dialog.show(childFragmentManager, "")
        }
        return view
    }


    class WizardPagerAdapter(val views: List<View>) : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            return views[position]
        }

        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsPager?.adapter = null
    }
}