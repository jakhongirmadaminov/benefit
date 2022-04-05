package uz.magnumactive.benefit.remote.repository

import uz.magnumactive.benefit.remote.models.BankBranchDTO
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.util.ResultWrapper

interface PartnersRemote {

    suspend fun getPartners(): ResultWrapper<List<Partner>>
    suspend fun getPartnersCategory(): ResultWrapper<List<PartnerCategoryDTO>>
    suspend fun getPartnersForCategory(id: Int): ResultWrapper<List<PartnerCategoryDTO>>
    suspend fun getAllBankBranches(): ResultWrapper<List<BankBranchDTO>>
    suspend fun getPartnersByCategoryId(id: Long): ResultWrapper<List<Partner>>

}