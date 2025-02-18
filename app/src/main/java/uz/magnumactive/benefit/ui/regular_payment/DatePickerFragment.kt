package uz.magnumactive.benefit.ui.regular_payment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.joda.time.DateTime
import java.util.*

const val KEY_DATE_SELECTED = "KET_DATE_SELECTED"
const val ARG_DATE = "ARG_DATE"

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        parentFragmentManager.setFragmentResult(
            KEY_DATE_SELECTED,
            Bundle().apply {
                putLong(ARG_DATE, DateTime(year, month, day, 0, 0).millis)
            }
        )

    }
}