package edu.ucne.cristophermarte_p2_ap2.data.remote

import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val depositoManagerApi: DepositoManagerApi
) {
    suspend fun getDepositos(): List<DepositoDto> =
        depositoManagerApi.getDepositos()

    suspend fun saveDeposito(deposito: DepositoDto): DepositoDto =
        depositoManagerApi.saveDeposito(deposito)
}