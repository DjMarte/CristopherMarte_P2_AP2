package edu.ucne.cristophermarte_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entidades")
data class EntidadEntity(
    @PrimaryKey(autoGenerate = true)
    val entidadId: Int
)