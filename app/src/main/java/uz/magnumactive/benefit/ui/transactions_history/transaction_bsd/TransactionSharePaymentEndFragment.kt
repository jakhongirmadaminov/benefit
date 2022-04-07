package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.viewgroups.FriendItemWithAmount
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.SizeUtils
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionSharePaymentEndFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_transaction_share_payment_end) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionSharePaymentEndFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remainder = args.transactionDTO.amountWithoutTiyin
        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        clTopUpSuccess.layoutParams = clTopUpSuccess.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )
        }
        clTopUpLoading.layoutParams = clTopUpSuccess.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )
        }
        lblTopUpSuccess.text = getString(R.string.transaction_divided_successfully)
        lblSearching.text = getString(R.string.transaction_being_divided)
        tvTransferAmount.text =
            getString(
                R.string.divided_sum,
                DecimalFormat("#,###").format(args.transactionDTO.amountWithoutTiyin)
            )
        tvCommissions.isVisible = false



        tvTotalSum.text =
            getString(R.string.sum) + ": " + args.transactionDTO.amountWithoutTiyin + " UZS"

        tvSharingPeopleCount.text =
            getString(R.string.sharing_with_whom) + ": " + (args.contacts.size + 1)
        tvRemainingAmount.text =
            getString(R.string.left_amount) + ": " + DecimalFormat("#,###").format(args.transactionDTO.amountWithoutTiyin) + " UZS"

        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }
        setupContacts()

    }

    private fun setupContacts() {

        rvPaymentSharingContacts.adapter = adapter
        adapter.clear()

        viewModel.payersList.clear()
        viewModel.payersList.add(
            BenefitContactDTO(
                AppPrefs.userId,
                AppPrefs.phoneNumber,
                AppPrefs.firstName + " " + AppPrefs.lastName,
                AppPrefs.avatar,
                isMe = true,
            )
        )
        viewModel.payersList.addAll(args.contacts)

        viewModel.payersList.forEachIndexed { index, friend ->
            friend.payingAmount = null
            adapter.add(FriendItemWithAmount(friend) {
                calculateLeftAmount()
            })
        }

    }

    var remainder: Long = 0L

    private fun calculateLeftAmount() {

        remainder =
            (args.transactionDTO.amountWithoutTiyin - viewModel.payersList.sumOf {
                it.payingAmount ?: 0
            })

        tvRemainingAmount.text =
            getString(R.string.left_amount) + ": " + DecimalFormat("#,###").format(remainder) + " UZS"

        tvRemainingAmount.setTextColor(
            ContextCompat.getColor(
                requireContext(), if (remainder < 0) {
                    R.color.error_text
                } else {
                    R.color.grey_text
                }
            )
        )

        updateShareButtonState()

    }

    private fun updateShareButtonState() {

        tvShare.isEnabled =
            remainder >= 0 && viewModel.payersList.none { it.payingAmount == null || it.payingAmount!! <= 0 }
    }

    private fun subscribeObservers() {

        viewModel.shareResponse.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    clTopUpSuccess.isVisible = false
                    clTopUpLoading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    clTopUpSuccess.isVisible = false
                    clTopUpLoading.isVisible = true

                }
                is RequestState.Success -> {
                    clTopUpSuccess.isVisible = true
                    clTopUpLoading.isVisible = false
                }
            }
        }
    }

    private fun attachListeners() {
        btnClose.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as TransactionBSD).dismiss()
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        tvShare.setOnClickListener {
            viewModel.shareTransaction(
                args.transactionDTO.utrnno!!,
                args.transactionDTO.amountWithoutTiyin,
            )
        }

        ivEqualize.setOnClickListener {
            val equalizedAmount = args.transactionDTO.amountWithoutTiyin / adapter.itemCount
            for (i in 0 until adapter.itemCount) {
                val item = adapter.getItem(i) as FriendItemWithAmount
                item.setAmount(equalizedAmount)
            }
        }


    }


}
