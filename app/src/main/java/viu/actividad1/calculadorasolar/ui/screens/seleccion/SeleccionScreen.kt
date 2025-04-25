package viu.actividad1.calculadorasolar.ui.screens.seleccion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viu.actividad1.calculadorasolar.ui.components.InputField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material.icons.filled.Delete

@Composable
fun SeleccionScreen(
    viewModel: SeleccionViewModel,
    onCalcular: (Double) -> Unit
) {
    val disponibles by viewModel.listaDisponibles.collectAsState()
    val seleccionados by viewModel.seleccionados.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var nombreNuevo by remember { mutableStateOf("") }
    var kwNuevo by remember { mutableStateOf("") }
    var consumoCalculado by remember { mutableStateOf(0.0) }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Selecciona tus electrodomésticos",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar electrodoméstico")
                }
            }

            Text("Disponibles:", style = MaterialTheme.typography.labelLarge)
            LazyColumn(modifier = Modifier.weight(2f)) {
                items(disponibles) { item ->
                    Button(
                        onClick = { viewModel.agregarAEleccion(item) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("${item.nombre} (${item.potenciaWatts}W)")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Seleccionados:", style = MaterialTheme.typography.labelLarge)
            LazyColumn(modifier = Modifier.weight(1.5f)) {
                itemsIndexed(seleccionados) { index, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${item.electrodomestico.nombre} (${item.electrodomestico.potenciaWatts}W)")
                                IconButton(onClick = {
                                    viewModel.eliminarSeleccionado(index)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }

                            var cantidad by rememberSaveable { mutableStateOf(item.cantidad.toString()) }
                            var horas by rememberSaveable { mutableStateOf(item.horasPorDia.toString()) }

                            Column(modifier = Modifier.fillMaxWidth()) {
                                InputField("Cantidad", cantidad, {
                                    cantidad = it
                                    viewModel.actualizarDatos(
                                        index,
                                        it.toIntOrNull() ?: 1,
                                        horas.toDoubleOrNull() ?: 1.0
                                    )
                                })

                                Spacer(modifier = Modifier.height(8.dp))

                                InputField("Horas/día", horas, {
                                    horas = it
                                    viewModel.actualizarDatos(
                                        index,
                                        cantidad.toIntOrNull() ?: 1,
                                        it.toDoubleOrNull() ?: 1.0
                                    )
                                })
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    consumoCalculado = viewModel.calcularConsumoTotal()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Consumo Estimado")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (consumoCalculado > 0.0) {
                val panelesNecesarios = (consumoCalculado / 300.0).toInt().coerceAtLeast(1)
                val energiaEstimadaykwh = panelesNecesarios * 300.0

                Text(
                    "🔋 Consumo estimado: ${"%.2f".format(consumoCalculado)} kWh/mes",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "☀️ Paneles necesarios: $panelesNecesarios (Generan $energiaEstimadaykwh kWh/mes)",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.guardarCalculoDesdeSeleccion(
                            consumoMensual = consumoCalculado,
                            paneles = panelesNecesarios,
                            energia = energiaEstimadaykwh
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cálculo")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nuevo Electrodoméstico") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = nombreNuevo,
                            onValueChange = { nombreNuevo = it },
                            label = { Text("Nombre") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = kwNuevo,
                            onValueChange = { kwNuevo = it },
                            label = { Text("Consumo (kW)") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val nombre = nombreNuevo.trim()
                        val kw = kwNuevo.toDoubleOrNull() ?: 0.0
                        if (nombre.isNotEmpty() && kw > 0.0) {
                            viewModel.agregarNuevoElectrodomestico(nombre, kw)
                            nombreNuevo = ""
                            kwNuevo = ""
                            showDialog = false
                        }
                    }) {
                        Text("Agregar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
