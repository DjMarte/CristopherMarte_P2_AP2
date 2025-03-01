package edu.ucne.cristophermarte_p2_ap2.presentation.depositos

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    depositoId: Int,
    goBackToList: () -> Unit
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    DepositoBodyScreen(
        viewModel = viewModel,
        uiState = uiState.value,
        goBackToList,
        depositoId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBodyScreen(
    viewModel: DepositoViewModel,
    uiState: DepositoUiState,
    goBackToList: () -> Unit,
    depositoId: Int
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(depositoId) {
        if (depositoId > 0)
            viewModel.find(depositoId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (depositoId > 0) "Editar deposito" else "Agregar deposito")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Campo Cliente
                    OutlinedTextField(
                        label = { Text("Concepto") },
                        value = uiState.concepto,
                        onValueChange = viewModel::onConceptoChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Fecha
                    OutlinedButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    val fec = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                                    viewModel.onFechaChange(fec)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .size(200.dp, 50.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Seleccionar Fecha",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = if (!uiState.fechaSeleccionada) "Seleccionar Fecha" else uiState.fecha)
                    }

                    // Campo Monto
                    OutlinedTextField(
                        label = { Text(text = "Costo") },
                        value = if (uiState.monto == 0.0) "" else uiState.monto.toString(),
                        onValueChange = { value ->
                            value.toDoubleOrNull()?.let { viewModel.onMontoChange(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    // Botones Nuevo y Guardar
                    if(depositoId <= 0){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Botón Nuevo
                            OutlinedButton(onClick = {
                                viewModel.new()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo",
                                    tint = Color.Blue
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Nuevo")
                            }

                            // Botón Guardar
                            OutlinedButton(onClick = {
                                viewModel.save()

                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Guardar",
                                    tint = Color.Green
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Guardar")
                            }
                        }
                    }
                    else{
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Botón Modificar
                            OutlinedButton(onClick = {
                                viewModel.save()
                                goBackToList()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Modificar",
                                    tint = Color.Green
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Modificar")
                            }

                            // Boton Eliminar
                            OutlinedButton(
                                onClick = {
                                    viewModel.delete(depositoId)
                                    goBackToList()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
