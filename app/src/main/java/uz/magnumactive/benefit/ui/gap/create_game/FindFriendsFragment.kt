package uz.magnumactive.benefit.ui.gap.create_game

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
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.remote.models.BenefitFriends
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.main.fill_card.REQ_CODE_READ_CONTACTS
import uz.magnumactive.benefit.ui.viewgroups.BenefitFriendItem
import uz.magnumactive.benefit.util.RequestState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_find_friends.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class FindFriendsFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_find_friends),
    LoaderManager.LoaderCallbacks<Cursor> {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: CreateGameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
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
        adapter.clear()
        value.forEach {
            it.isChecked = viewModel.selectedFriends.contains(it) == true
            adapter.add(BenefitFriendItem(it, onCheckChanged = { isChecked ->
                if (isChecked) viewModel.selectedFriends.add(it)
                else viewModel.selectedFriends.remove(it)
            }))
        }
    }

    private fun attachListeners() {

        edtSearch.doOnTextChanged { text, start, before, count ->
            when (val result = viewModel.availableContacts.value) {
                is RequestState.Success -> {
                    result.value.let { benefitContacts ->
                        if (text!!.isBlank()) {
                            adapter.clear()
                            loadData(benefitContacts)
                        } else {
                            val filtered = benefitContacts.filter {
                                it.fullname?.lowercase()
                                    ?.contains(text.toString().lowercase()) == true
                                /* || it.titleShort?.contains(text) == true*/
                            }
                            if (filtered.isNotEmpty()) {
                                adapter.clear()
                                loadData(filtered)
                            }
                        }
                    }
                }
                else -> {

                }
            }
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
//        ivLocation.setOnClickListener { }

        tvSelect.setOnClickListener {
            val contacts = BenefitFriends()
            for (i in 0 until adapter.itemCount) {
                val friend = (adapter.getItem(i) as BenefitFriendItem).friend
                if (friend.isChecked) contacts.add(friend)
            }
            viewModel.selectedFriends = contacts
            findNavController().popBackStack()
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
                    // Initializes the loader
                    LoaderManager.getInstance(this).initLoader(0, null, this)

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


//    @SuppressLint("InlinedApi")
//    private val PROJECTION: Array<out String> = arrayOf(
//        ContactsContract.Contacts._ID,
//        ContactsContract.Contacts.LOOKUP_KEY,
//        ContactsContract.Contacts.DISPLAY_NAME
//    )
//
//    // Defines the text expression
//    @SuppressLint("InlinedApi")
//    private val SELECTION: String = "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"

    // Defines a variable for the search string
//    private val searchString: String = ""

    // Defines the array to hold values that replace the ?
//    private val selectionArgs = arrayOf(searchString)


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        /*
           * Makes search string into pattern and
           * stores it in the selection array
           */
//        selectionArgs[0] = "%$searchString%"
        // Starts the query
        return activity?.let {
            contactsLoader()!!
        } ?: throw IllegalStateException()
    }

    private fun contactsLoader(): Loader<Cursor>? {
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
        val sortOrder: String? = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
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
        data.use {
            while (it.moveToNext()) {
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                if (phone.length >= 9) {
                    val formattedPhone = phone.replace(" ", "").removePrefix("+998")
                    if (formattedPhone.length == 9) {
                        contacts.append("$formattedPhone, ")
                    }
                }
            }
        }

        if (contacts.isNotBlank()) {
            contacts.removeSuffix(", ")
        }

        viewModel.checkMyContacts(contacts.toString())
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
//        cursorAdapter?.swapCursor(null)
    }


}
