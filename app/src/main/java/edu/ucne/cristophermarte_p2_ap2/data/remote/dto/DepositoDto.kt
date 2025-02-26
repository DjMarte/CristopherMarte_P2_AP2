package edu.ucne.cristophermarte_p2_ap2.data.remote.dto

import java.util.Date

data class DepositoDto(
    val depositoId: Int,
    val fecha: Date,
    val cuentaId: Int,
    val concepto: String,
    val monto: Double
)