package edu.ucne.cristophermarte_p2_ap2.data.remote

import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepositoManagerApi {
    @Headers("X-API-Key:test")
    @GET("/api/Depositos")
    suspend fun getDepositos(): List<DepositoDto>

    @Headers("X-API-Key:test")
    @POST("/api/Depositos")
    suspend fun saveDeposito(@Body deposito: DepositoDto?): DepositoDto

    @Headers("X-API-Key:test")
    @GET("/api/Depositos/{depositoId}")
    suspend fun getDepositoById(@Path("depositoId") depositoId: Int): DepositoDto

    @Headers("X-API-Key:test")
    @PUT("/api/Depositos/{depositoId}")
    suspend fun updateDeposito(
        @Path("depositoId") depositoId: Int,
        @Body deposito: DepositoDto
    ): Response<DepositoDto>

    @Headers("X-API-Key:test")
    @DELETE("/api/Depositos/{depositoId}")
    suspend fun deleteDeposito(@Path("depositoId") depositoId: Int): Response<Unit>
}