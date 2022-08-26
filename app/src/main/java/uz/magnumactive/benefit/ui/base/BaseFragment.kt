package uz.magnumactive.benefit.ui.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dagger.hilt.android.AndroidEntryPoint
import uz.magnumactive.benefit.util.RequestState

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {


//    open fun attach() {
//
//    }
//
//    open fun setup() {
//
//    }
//
//    open fun observe() {
//
//    }

    fun <T> setupRVObservers(
        observable: LiveData<RequestState<T>>,
        progressView: View?,
        recyclerView: RecyclerView,
        adapter: GroupAdapter<GroupieViewHolder>,
        createItem: (obj: T) -> Item
    ) {
        observable.observe(viewLifecycleOwner) { requestState ->
            val resp = requestState ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    progressView?.isVisible = false
                    recyclerView?.isVisible = false
                }
                RequestState.Loading -> {
                    progressView?.isVisible = true
                    recyclerView?.isVisible = false
                }
                is RequestState.Success -> {
                    progressView?.isVisible = false
                    recyclerView?.isVisible = true
                    loadData(adapter, resp.value as List<T>) {
                        createItem(it)
                    }
                }
            }
        }

    }


    private fun <T> loadData(
        adapter: GroupAdapter<GroupieViewHolder>,
        value: List<T>,
        createItem: (T) -> Item,
    ) {
        adapter.clear()
        value.forEach {
            adapter.add(createItem(it))
        }
        adapter.notifyDataSetChanged()
    }

}