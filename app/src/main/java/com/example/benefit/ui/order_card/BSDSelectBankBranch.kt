package com.example.benefit.ui.order_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_select_bank_branch.*


@AndroidEntryPoint
class BSDSelectBankBranch : MyBSDialog() {


    private val viewModel: BSDSelectBranchViewModel by viewModels()

    lateinit var adapter: BankBranchesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.bsd_select_bank_branch, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BankBranchesAdapter {
            (activity as IOnBranchSelected).onBranchSelected(it)
            dismiss()
        }

        rvBranches.adapter = adapter

        viewModel.getBranches()
        attachListeners()
        subscribeObservers()

    }

    private fun attachListeners() {

        ivBack.setOnClickListener {
            dismiss()
        }

        edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun subscribeObservers() {
        viewModel.branches.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        })

    }


}
