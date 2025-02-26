package edu.ucne.cristophermarte_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.EntidadListScreen
    ) {
        composable<Screen.EntidadListScreen> {
            /*EntidadListScreen(
                onAddEntidad = {navHostController.navigate(Screen.Entidad(0))},
                goToEntidadScreen = { entidadId ->
                    navHostController.navigate(Screen.Entidad(entidadId = entidadId))
                }
            )

             */
        }
        composable<Screen.Entidad> {
            val entidadId = it.toRoute<Screen.Entidad>().entidadId
            /*EntidadScreen(
                entidadId = entidadId,
                goBackToList = {navHostController.navigateUp()}
            )

             */
        }
    }
}