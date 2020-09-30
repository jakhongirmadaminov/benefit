package com.example.benefit.remote.repository

import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.remote.models.PartnerDTO
import com.example.benefit.util.ResultWrapper

interface PartnersRemote {

    suspend fun getPartners(): ResultWrapper<List<PartnerDTO>>
    suspend fun getPartnersCategory(): ResultWrapper<List<PartnerCategoryDTO>>
    suspend fun getPartnersForCategory(id: Int): ResultWrapper<List<PartnerCategoryDTO>>


}