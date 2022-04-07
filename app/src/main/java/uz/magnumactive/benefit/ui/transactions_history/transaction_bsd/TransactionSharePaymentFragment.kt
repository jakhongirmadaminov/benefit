package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_transaction_share_payment.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.remote.models.BenefitFriends
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.main.fill_card.REQ_CODE_READ_CONTACTS
import uz.magnumactive.benefit.ui.viewgroups.FriendItem
import uz.magnumactive.benefit.util.RequestState
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionSharePaymentFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_transaction_share_payment),
    LoaderManager.LoaderCallbacks<Cursor> {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionSharePaymentFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
//        viewModel.getFriends()
    }

    private fun setupViews() {
        rvContacts.adapter = adapter

        checkForReadContactsPermission {
            // Initializes the loader
            LoaderManager.getInstance(this).initLoader(0, null, this)
        }


    }


    private fun checkForReadContactsPermission(action: () -> Unit) {

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) -> {
                action()
            }
            else -> {
                // You can directly ask for the permission.
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQ_CODE_READ_CONTACTS
                )
            }
        }
    }


    private fun subscribeObservers() {


        viewModel.availableContacts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadData(result.value)
                }
            }
        }

    }

    private fun loadData(value: List<BenefitContactDTO>) {
        viewModel.selectedContacts.clear()
        viewModel.selectedContacts =
            BenefitFriends().apply { addAll(value.filter { it.isChecked }) }
        adapter.clear()
        if (value.isEmpty()) {

        }
        value.forEach {
            adapter.add(FriendItem(it) {
                checkIfAnySelected()
            })
        }
    }

    private fun checkIfAnySelected() {

        viewModel.selectedContacts.clear()
        viewModel.selectedContacts = BenefitFriends().apply {
            addAll((viewModel.availableContacts.value as? RequestState.Success)?.value?.filter { it.isChecked }
                ?: arrayListOf())
        }
        tvSelect.isEnabled = viewModel.selectedContacts.isNotEmpty()

    }

    private fun attachListeners() {

        edtSearch.doOnTextChanged { text, start, before, count ->
            (viewModel.availableContacts.value as? RequestState.Success)?.value?.let { friendsList ->
                adapter.clear()
                val filtered = friendsList.filter {
                    it.fullname?.lowercase()?.contains(text.toString().lowercase()) ?: false
                }
                filtered.forEach {
                    adapter.add(FriendItem(it) {})
                }
            }
        }


        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
//        ivLocation.setOnClickListener {
//            findNavController().navigate(
//                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSearchFriendNearbyFragment()
//            )
//        }

        tvSelect.setOnClickListener {
            println("SSSS" + viewModel.selectedContacts)
            findNavController().navigate(
                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSharePaymentEndFragment(
                    viewModel.selectedContacts,
                    args.transactionDTO
                )
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQ_CODE_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    LoaderManager.getInstance(this).initLoader(0, null, this)
                }
                return
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return activity?.let { contactsLoader() } ?: throw IllegalStateException()
    }

    private fun contactsLoader(): Loader<Cursor> {
        val contactsUri =
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI // The content URI of the phone contacts
        val projection = arrayOf( // The columns to return for each row
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.Contacts.LOOKUP_KEY
        )
        val selection: String? = null //Selection criteria
        val selectionArgs = arrayOf<String>() //Selection criteria
        val sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        return CursorLoader(
            requireContext(),
            contactsUri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        // Put the result Cursor in the adapter for the ListView
//        cursorAdapter?.swapCursor(data)
        val contacts = StringBuilder("")
        try {
            while (data.moveToNext()) {
                val phone =
                    data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                if (phone.length >= 9) {
                    val formattedPhone = phone.replace(" ", "").removePrefix("+998")
                    if (formattedPhone.length == 9) {
                        contacts.append("$formattedPhone, ")
                    }
                }
            }
        } catch (e: Exception) {

        } finally {
            data.close()
        }


        if (contacts.isNotBlank()) {
            contacts.removeSuffix(", ")
        }

        viewModel.checkMyContacts(contacts.toString())
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
//        cursorAdapter?.swapCursor(null)
//        cursorLoader?.reset()
    }


}
