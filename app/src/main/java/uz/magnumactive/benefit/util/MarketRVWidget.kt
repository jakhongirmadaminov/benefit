//package uz.magnumactive.benefit.util
//
//import android.content.Context
//import android.util.AttributeSet
//import androidx.core.view.isVisible
//import androidx.lifecycle.LiveData
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class MarketRVWidget(context: Context, attributeSet: AttributeSet) :
//    RecyclerView(context, attributeSet) {
//
//    init {
//        layoutManager = LinearLayoutManager(context)
//    }
//
//
//    fun <T> subscribeTo(
//        observable: LiveData<RequestState<T>>,
//
//        ) {
//
//        observable.observe( viewLifecycleOwner) { requestState ->
//            val resp = requestState ?: return@observe
//            when (resp) {
//                is RequestState.Error -> {
//                    progressView?.isVisible = false
//                    recyclerView?.isVisible = false
//                }
//                RequestState.Loading -> {
//                    progressView?.isVisible = true
//                    recyclerView?.isVisible = false
//                }
//                is RequestState.Success -> {
//                    progressView?.isVisible = false
//                    recyclerView?.isVisible = true
//                    loadData(adapter, resp.value as List<T>) {
//                        createItem(it)
//                    }
//                }
//            }
//        }
//
//    }
//
//
//}