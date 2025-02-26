package edu.ucne.cristophermarte_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    data object EntidadListScreen : Screen() // Consulta

    @Serializable
    data class Entidad(val entidadId: Int) : Screen() // Registro

}