package uz.magnumactive.benefit.ui.main.home.card_options

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import uz.magnumactive.benefit.R
import kotlinx.android.synthetic.main.dialog_block_card.*

/**
 * Created by jahon on 14-Sep-20
 */
class DialogBlockCard : DialogFragment() {

    lateinit var listener: IOnCardAction
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (targetFragment is IOnCardAction) {
            listener = targetFragment as IOnCardAction
        } else {
            throw ClassCastException("$context must implement IOnCardAction")
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
        return inflater.inflate(R.layout.dialog_block_card, container)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnBlock.setOnClickListener {
            listener.onCardBlockClick()
            dismiss()
        }
    }

}