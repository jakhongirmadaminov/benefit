package com.example.benefit.remote.repository

import com.example.benefit.remote.models.BankBranchDTO
import com.example.benefit.remote.models.Partner
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.ResultWrapper

interface PartnersRemote {

    suspend fun getPartners(): ResultWrapper<List<Partner>>
    suspend fun getPartnersCategory(): ResultWrapper<List<PartnerCategoryDTO>>
    suspend fun getPartnersForCategory(id: Int): ResultWrapper<List<PartnerCategoryDTO>>
    suspend fun getAllBankBranches(): ResultWrapper<List<BankBranchDTO>>
    suspend fun getPartnersByCategoryId(id: Int): ResultWrapper<List<Partner>>

}