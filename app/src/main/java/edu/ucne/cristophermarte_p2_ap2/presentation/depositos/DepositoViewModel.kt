package edu.ucne.cristophermarte_p2_ap2.presentation.depositos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.cristophermarte_p2_ap2.data.remote.Resource
import edu.ucne.cristophermarte_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.cristophermarte_p2_ap2.data.repository.DepositoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllDepositos()
    }

    fun save(){
        viewModelScope.launch{
            if(isValidate()) {
                depositoRepository.save(_uiState.value.toEntity())
                _uiState.update { it.copy(errorMessage = null) }
                new()
            }
        }
    }


    fun update(){
        viewModelScope.launch {
            depositoRepository.update(
                _uiState.value.depositoId, DepositoDto(
                    idDeposito = _uiState.value.depositoId,
                    fecha = _uiState.value.fecha,
                    idCuenta = _uiState.value.cuentaId,
                    concepto = _uiState.value.concepto,
                    monto = _uiState.value.monto
                )
            )
        }
    }

    fun find(depositoId: Int){
        viewModelScope.launch {
            if(depositoId > 0){
                val deposito = depositoRepository.find(depositoId)
                if(deposito.idDeposito != 0){
                    _uiState.update {
                        it.copy(
                            depositoId = deposito.idDeposito,
                            fecha = deposito.fecha,
                            cuentaId = deposito.idCuenta,
                            concepto = deposito.concepto,
                            monto = deposito.monto
                        )
                    }
                }
            }
        }
    }

    fun new(){
        _uiState.value = DepositoUiState()
    }

    fun onConceptoChange(concepto: String){
        val descripcionRegularExpression = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s'-]+$".toRegex()
        _uiState.update {
            it.copy(
                concepto = concepto,
                errorMessage = if (concepto.isBlank()) "el concepto no puede estar vacío"
                else if (!concepto.matches(descripcionRegularExpression)) "El concepto solo puede contener letras y números"
                else null
            )
        }
    }


    fun onMontoChange(monto: Double) {
        _uiState.update {
            it.copy(
                monto = monto,
                errorMessage = if (monto <= 0) "El monto debe ser mayor a 0"
                else null
            )
        }
    }

    fun onFechaChange(fecha: String) {
        _uiState.update {
            it.copy(
                fecha = fecha,
                fechaSeleccionada = true
            )
        }
    }

    fun getAllDepositos(){
        viewModelScope.launch {
            depositoRepository.getAllDepositos().collectLatest { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                listadeposito = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        val state = uiState.value

        if (state.concepto.isBlank() || state.monto <= 0 || state.fecha == null) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son requeridos.") }
            return false
        }
        return true
    }
}
fun DepositoUiState.toEntity() = DepositoDto(
    idDeposito = depositoId,
    fecha = fecha,
    idCuenta = cuentaId,
    concepto = concepto,
    monto = monto
)