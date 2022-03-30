package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.FriendDTO
import uz.magnumactive.benefit.remote.models.Friends
import uz.magnumactive.benefit.ui.viewgroups.FriendItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_transaction_share_payment.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionSharePaymentFragment @Inject constructor() :
    Fragment(R.layout.fragment_transaction_share_payment) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionSharePaymentFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        setupContacts()

    }

    private fun setupContacts() {

        rvContacts.adapter = adapter

        val data = listOf(
            FriendDTO("Алексей", "Иванов", "", ""),
            FriendDTO("Константин", "Игнатьев", "", ""),
            FriendDTO("Марина", "Авазова", "", ""),
            FriendDTO("Екатерина", "Петрова", "", ""),
            FriendDTO("Марат", "Измайлов", "", ""),
            FriendDTO("Валентин", "Автухов", "", ""),
        )

        data.forEach {
            adapter.add(FriendItem(it) {
            })
        }


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivLocation.setOnClickListener {
            findNavController().navigate(
                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSearchFriendNearbyFragment()
            )
        }

        tvSelect.setOnClickListener {
            val contacts = Friends()

            for (i in 0 until adapter.itemCount) {
                contacts.add((adapter.getItem(i) as FriendItem).friend)
            }

            findNavController().navigate(
                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSharePaymentSelectedFragment(
                    contacts
                )
            )
        }
    }


}
