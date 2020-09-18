package com.example.benefit.ui.main.fill_card

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.FriendDTO
import com.example.benefit.ui.main.home.card_options.CardOptionsViewModel
import com.example.benefit.ui.viewgroups.FriendItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fill_card_ask_friends.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

/*
 * Defines an array that contains column names to move from
 * the Cursor to the ListView.
 */
@SuppressLint("InlinedApi")
private val FROM_COLUMNS: Array<String> = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

// The column index for the _ID column
private const val CONTACT_ID_INDEX: Int = 0

// The column index for the CONTACT_KEY column
private const val CONTACT_KEY_INDEX: Int = 1

/*
 * Defines an array that contains resource ids for the layout views
 * that get the Cursor column contents. The id is pre-defined in
 * the Android framework, so it is prefaced with "android.R.id"
 */
private val TO_IDS: IntArray = intArrayOf(android.R.id.text1)


@AndroidEntryPoint
class FillCardAskFriendsFragment @Inject constructor() :
    Fragment(R.layout.fragment_fill_card_ask_friends),
    LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

    // Define variables for the contact the user selects
    // The contact's _ID value
    var contactId: Long = 0

    // The contact's LOOKUP_KEY
    var contactKey: String? = null

    // A content URI for the selected contact
    var contactUri: Uri? = null

    // An adapter that binds the result Cursor to the ListView
    private var cursorAdapter = FriendsCursorAdapter()

    private val REQ_CODE_READ_CONTACTS = 12
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: CardOptionsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {


//        rvContacts.adapter = adapter

        val data = listOf(
            FriendDTO("Doniyor", "Zuparov"),
            FriendDTO("Name", "LastName"),
            FriendDTO("Name", "LastName"),
            FriendDTO("Name", "LastName"),
            FriendDTO("Name", "LastName"),
            FriendDTO("Name", "LastName"),
        )
        loadContacts(data)
    }

    private fun loadContacts(data: List<FriendDTO>) {

        checkForReadContactsPermission {

            // Initializes the loader
            LoaderManager.getInstance(this).initLoader(0, null, this)

        }




        adapter.clear()

        if (data.isEmpty()) {

//            adapter.add(InfoItem(getString(R.string.no_friends_found)))
        } else {
            data.forEach {
                adapter.add(FriendItem(it))
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun checkForReadContactsPermission(action: () -> Unit) {

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
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


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        ivLocation.setOnClickListener {

        }
        tvSelect.setOnClickListener {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Gets the ListView from the View list of the parent activity
        activity?.also {
            // Gets a CursorAdapter

            // Sets the adapter for the ListView
            rvContacts.adapter = cursorAdapter
        }

//        rvContacts.onItemClickListener = this
    }

    @SuppressLint("InlinedApi")
    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.DISPLAY_NAME
    )

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private val SELECTION: String = "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"

    // Defines a variable for the search string
    private val searchString: String = ""

    // Defines the array to hold values that replace the ?
    private val selectionArgs = arrayOf(searchString)


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        /*
           * Makes search string into pattern and
           * stores it in the selection array
           */
        selectionArgs[0] = "%$searchString%"
        // Starts the query
        return activity?.let {
            contactsLoader()!!
        } ?: throw IllegalStateException()
    }

    private fun contactsLoader(): Loader<Cursor>? {
        val contactsUri =
            ContactsContract.Contacts.CONTENT_URI // The content URI of the phone contacts
        val projection = arrayOf( // The columns to return for each row
            ContactsContract.Contacts.DISPLAY_NAME
        )
        val selection: String? = null //Selection criteria
        val selectionArgs = arrayOf<String>() //Selection criteria
        val sortOrder: String? = null //The sort order for the returned rows
        return CursorLoader(
            requireContext(),
            contactsUri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        // Put the result Cursor in the adapter for the ListView
        cursorAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
        cursorAdapter?.swapCursor(null)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Get the Cursor
        val cursor: Cursor? = (parent!!.adapter as? CursorAdapter)?.cursor?.apply {
            // Move to the selected contact
            moveToPosition(position)
            // Get the _ID value
            contactId = getLong(CONTACT_ID_INDEX)
            // Get the selected LOOKUP KEY
            contactKey = getString(CONTACT_KEY_INDEX)
            // Create the contact's content Uri
            contactUri = ContactsContract.Contacts.getLookupUri(contactId, null)
            /*
             * You can use contactUri as the content URI for retrieving
             * the details for a contact.
             */
        }
    }

}