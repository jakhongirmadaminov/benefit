package uz.magnumactive.benefit.ui.auth.registration

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import uz.magnumactive.benefit.R
import kotlinx.android.synthetic.main.dialog_month_selector.*
import org.joda.time.DateTime

class DialogDOBSelector : DialogFragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrationEndFragment.
         */
        @JvmStatic
        fun newInstance(target: Fragment) =
            DialogDOBSelector().apply {
                setTargetFragment(target, 2)
                arguments = Bundle().apply {
//                    putSerializable(ARG_PARAM2, dateTo)
                }
            }
    }

    lateinit var listener: IOnMonthSelected
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (targetFragment is IOnMonthSelected) {
            listener = targetFragment as IOnMonthSelected
        } else {
            throw ClassCastException("$context must implement IOnMonthSelected")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_month_selector, container)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        btnSelect.setOnClickListener {
            listener.onDOBSelected(datePicker.dayOfMonth, datePicker.month, datePicker.year)
            dismiss()
        }

        datePicker.maxDate = DateTime.now().millis

    }
}

interface IOnMonthSelected {
    fun onDOBSelected(day: Int, month: Int, year: Int)
}
