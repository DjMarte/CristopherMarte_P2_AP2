package edu.ucne.cristophermarte_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.cristophermarte_p2_ap2.data.local.entity.DepositoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositoDao {
    @Upsert
    suspend fun save(listaDeposito: List<DepositoEntity>)

    @Query(
        """
            Select * From Depositos 
            Where idDeposito = :idDeposito
            Limit 1
        """
    )
    suspend fun find(idDeposito: Int): DepositoEntity?

    @Delete
    suspend fun delete(deposito: DepositoEntity)

    @Query("Select * From Depositos")
    fun getAll(): Flow<List<DepositoEntity>>
}