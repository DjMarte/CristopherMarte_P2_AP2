package edu.ucne.cristophermarte_p2_ap2.data.repository

import android.util.Log
import edu.ucne.cristophermarte_p2_ap2.data.remote.RemoteDataSource
import edu.ucne.cristophermarte_p2_ap2.data.remote.Resource
import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAllDepositos(): Flow<Resource<List<DepositoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val depositos = remoteDataSource.getDepositos()
            emit(Resource.Success(depositos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("DepositoRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    suspend fun save(depositoDto: DepositoDto): DepositoDto =
        remoteDataSource.saveDeposito(depositoDto)

    suspend fun find(id: Int): DepositoDto =
        remoteDataSource.getDepositoById(id)

    suspend fun update(id: Int, depositoDto: DepositoDto) =
        remoteDataSource.updateDeposito(id, depositoDto)
}