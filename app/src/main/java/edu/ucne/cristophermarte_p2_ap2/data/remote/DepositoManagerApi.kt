package edu.ucne.cristophermarte_p2_ap2.data.remote

import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DepositoManagerApi {
    @GET("/api/Depositos")
    suspend fun getDepositos(): List<DepositoDto>

    @POST("/api/Depositos")
    suspend fun saveDeposito(@Body deposito: DepositoDto?): DepositoDto
}