package edu.ucne.cristophermarte_p2_ap2.data.repository

import edu.ucne.cristophermarte_p2_ap2.data.local.dao.DepositoDao
import edu.ucne.cristophermarte_p2_ap2.data.local.entity.DepositoEntity
import edu.ucne.cristophermarte_p2_ap2.data.remote.RemoteDataSource
import edu.ucne.cristophermarte_p2_ap2.data.remote.Resource
import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val depositoDao: DepositoDao,
    private val remoteDataSource: RemoteDataSource
) {
    fun getAllDepositos(): Flow<Resource<List<DepositoEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val depositoRemoto = remoteDataSource.getDepositos()

            val listaDepositos = depositoRemoto.map { dto ->
                DepositoEntity(
                    idDeposito = dto.idDeposito,
                    fecha = dto.fecha,
                    idCuenta = dto.idCuenta,
                    concepto = dto.concepto,
                    monto = dto.monto
                )
            }
            depositoDao.save(listaDepositos)
            emit(Resource.Success(listaDepositos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexión $errorMessage"))
        } catch (e: Exception) {
            val depositosLocales = depositoDao.getAll().first()
            if (depositosLocales.isNotEmpty())
                emit(Resource.Success(depositosLocales))
            else
                emit(Resource.Error("Error de conexión: ${e.message}"))
        }
    }

    suspend fun save(depositoDto: DepositoDto): DepositoDto =
        remoteDataSource.saveDeposito(depositoDto)

    suspend fun find(id: Int): DepositoEntity? {
        val depositosDto = remoteDataSource.getDepositos()
        return depositosDto
            .firstOrNull { it.idDeposito == id }
            ?.let { depositoDto ->
                DepositoEntity(
                    idDeposito = depositoDto.idDeposito,
                    fecha = depositoDto.fecha,
                    idCuenta = depositoDto.idCuenta,
                    concepto = depositoDto.concepto,
                    monto = depositoDto.monto
                )
            }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) =
        remoteDataSource.updateDeposito(id, depositoDto)

    suspend fun delete(id: Int) =
        remoteDataSource.deleteDeposito(id)
}