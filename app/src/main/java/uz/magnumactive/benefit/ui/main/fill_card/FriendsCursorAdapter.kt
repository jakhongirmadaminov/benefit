package uz.magnumactive.benefit.ui.main.fill_card

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.FriendDTO
import uz.magnumactive.benefit.ui.main.fill_card.FriendsCursorAdapter.FriendViewHolder

/**
 * Created on : Jan 27, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
class FriendsCursorAdapter : uz.magnumactive.benefit.util.BaseCursorAdapter<FriendViewHolder>(null)/*, Filterable */ {

    val selectionList = ArrayList<FriendDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val formNameView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_with_cb, parent, false)
        return FriendViewHolder(formNameView)
    }


    override fun onBindViewHolder(holder: FriendViewHolder, cursor: Cursor?) {
        if (cursor == null) return
        val name = cursor.getString(
            cursor.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
        )
        val phoneNumber = cursor.getString(
            cursor.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
        )
        val photoUri = cursor.getString(
            cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
        )
        Log.d("NUMBEEER", name + phoneNumber)
        holder.nameTextView?.text = "$name $phoneNumber     photo   $photoUri"
        if (photoUri != null) holder.photo.setImageURI(Uri.parse(photoUri))

        holder.cb.isChecked = holder.isChecked

        holder.parentView.setOnClickListener {
            holder.isChecked = !holder.isChecked
            holder.cb.isChecked = holder.isChecked
            val contact = FriendDTO(name, "", phoneNumber, photoUri)

            if (holder.cb.isChecked) {
                if (!selectionList.contains(contact)) {
                    selectionList.add(contact)
                }
            } else {
                if (selectionList.contains(contact)) {
                    selectionList.remove(contact)
                }
            }
        }
    }

    override fun swapCursor(newCursor: Cursor?) {
        super.swapCursor(newCursor)
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.tvNameLastName)
        var cb: CheckBox = itemView.findViewById(R.id.checkBox)
        var parentView: View = itemView.findViewById(R.id.llContact)
        var photo: ImageView = itemView.findViewById(R.id.photo)

        var isChecked = false
    }

//   override fun getFilter(): Filter? {
//        return exampleFilter
//    }
//
//    private val exampleFilter: Filter = object : Filter() {
//        protected fun performFiltering(constraint: CharSequence?): FilterResults? {
//            val filteredList: MutableList<ExampleItem> = ArrayList()
//            if (constraint == null || constraint.length == 0) {
//                filteredList.addAll(exampleListFull)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
//                for (item in exampleListFull) {
//                    if (item.getText2().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        protected fun publishResults(constraint: CharSequence?, results: FilterResults) {
//            exampleList.clear()
//            exampleList.addAll(results.values as List<*>)
//            notifyDataSetChanged()
//        }
//    }

}