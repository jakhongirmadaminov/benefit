package uz.magnumactive.benefit.ui.marketplace

import dagger.hilt.android.lifecycle.HiltViewModel
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.ui.marketplace.cart.BaseBasketViewModel
import javax.inject.Inject

@HiltViewModel
class MarketActivityViewModel @Inject constructor(authApi: AuthApiService) :
    BaseBasketViewModel(authApi) {


}
