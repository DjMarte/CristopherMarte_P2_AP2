package edu.ucne.cristophermarte_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.cristophermarte_p2_ap2.data.local.entity.EntidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntidadDao {
    @Upsert
    suspend fun save(listaEntidad: List<EntidadEntity>)

    @Query(
        """
            Select * From Entidades 
            Where entidadId = :entidadId
            Limit 1
        """
    )
    suspend fun find(entidadId: Int): EntidadEntity?

    @Delete
    suspend fun delete(entidad: EntidadEntity)

    @Query("Select * From Entidades")
    fun getAll(): Flow<List<EntidadEntity>>
}