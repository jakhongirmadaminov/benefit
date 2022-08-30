package uz.magnumactive.benefit.ui.marketplace.cart

import dagger.hilt.android.lifecycle.HiltViewModel
import uz.magnumactive.benefit.remote.AuthApiService
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(authApi: AuthApiService) : BaseBasketViewModel(authApi) {


}
