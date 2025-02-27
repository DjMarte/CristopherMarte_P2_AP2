package edu.ucne.cristophermarte_p2_ap2.presentation.depositos

import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto

data class DepositoUiState(
    val depositoId: Int = 0,
    val fecha: String = "",
    val fechaSeleccionada: Boolean = false,
    val cuentaId: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val errorMessage: String? = null,
    val listadeposito: List<DepositoDto> = emptyList(),
    val isLoading: Boolean = false
)