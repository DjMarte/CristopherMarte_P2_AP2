package edu.ucne.cristophermarte_p2_ap2.presentation.depositos

import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import java.util.Date

data class DepositoUiState(
    val depositoId: Int = 0,
    val fecha: Date = Date(),
    val cuentaId: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val errorMessage: String? = null,
    val listadeposito: List<DepositoDto> = emptyList(),
    val isLoading: Boolean = false
)