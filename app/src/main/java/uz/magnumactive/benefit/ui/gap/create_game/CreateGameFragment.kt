package uz.magnumactive.benefit.ui.gap.create_game

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_create_game.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.gap.gap_chart.EXTRA_GAP_GAME
import uz.magnumactive.benefit.ui.gap.gap_chart.GapChartActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemContactSquare
import uz.magnumactive.benefit.util.RequestState

class CreateGameFragment : BaseFragment(R.layout.fragment_create_game) {

    private val viewModel: CreateGameViewModel by activityViewModels()
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        rvContacts.adapter = adapter
        adapter.clear()

        tvPlayerCount.text =
            getString(R.string.count) + ": " + viewModel.selectedFriends.size.toString()
        viewModel.selectedFriends.forEach { friend ->
            adapter.add(ItemContactSquare(friend,
                onClick = null,
                onRemove = { viewItem ->
                    viewModel.selectedFriends.remove(friend)
                    adapter.remove(viewItem)
                    tvPlayerCount.text =
                        getString(R.string.count) + ": " + viewModel.selectedFriends.size.toString()
                    checkFields()
                }
            ))
        }

        if (viewModel.selectedFriends.size < 10) {
            adapter.add(ItemContactSquare(onClick = {
                findNavController().navigate(
                    CreateGameFragmentDirections.actionCreateGameFragmentToFindFriendsFragment()
                )
            }, onRemove = null))
        }
        checkFields()
    }

    private fun subscribeObservers() {
        viewModel.createGameResp.observe(viewLifecycleOwner) {
            when (val resp = it) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    startActivity(Intent(requireActivity(), GapChartActivity::class.java).apply {
                        putExtra(EXTRA_GAP_GAME, resp.value)
                    })
                    ((parentFragment as NavHostFragment).parentFragment as CreateGameBSD).dismiss()
                }
            }
        }

    }

    private fun attachListeners() {
        tvCreate.setOnClickListener {
            val playersBuilder = StringBuilder("")
            viewModel.selectedFriends.forEachIndexed { index, benefitContactDTO ->
                playersBuilder.append(benefitContactDTO.user_id!!.toString() + ",")
            }

            viewModel.createGame(
                members = playersBuilder.removeSuffix(",").toString(),
                title = edtDesignation.text.toString(),
                summ = edtSummPerPerson.text.toString(),
                isRandom = if (switchSetup.isChecked) "yes" else "no",
                isNotif = if (switchNotification.isChecked) "yes" else "no",
                period = if (checkOnce.isChecked) "week" else if (checkOnceAWeek.isChecked) "week" else if (checkOnceAWeek.isChecked) "week" else if (checkOnceAWeek.isChecked) "week" else "week"
            )
        }

        edtDesignation.doOnTextChanged { text, start, before, count ->
            checkFields()
        }
        edtSummPerPerson.doOnTextChanged { text, start, before, count ->
            checkFields()
        }

    }

    private fun checkFields() {
        tvCreate.myEnabled(
            edtDesignation.text.isNotBlank()
                    && edtSummPerPerson.text.isNotBlank()
                    && viewModel.selectedFriends.size > 1
        )
    }

}