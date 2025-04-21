package viu.actividad1.calculadorasolar.ui.screens.seleccion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import viu.actividad1.calculadorasolar.ui.components.InputField

@Composable
fun SeleccionScreen(
    viewModel: SeleccionViewModel,
    onCalcular: (Double) -> Unit // Permite devolver el consumo total al pulsar "Calcular"
) {
    val disponibles by viewModel.listaDisponibles.collectAsState()
    val seleccionados by viewModel.seleccionados.collectAsState()

    // Estado local para guardar el resultado del cálculo
    var consumoCalculado by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Selecciona tus electrodomésticos", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Disponibles:", style = MaterialTheme.typography.labelLarge)
        LazyColumn(modifier = Modifier.weight(1f)) {
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
        LazyColumn(modifier = Modifier.weight(2f)) {
            itemsIndexed(seleccionados) { index, item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {

                        Text("${item.electrodomestico.nombre} (${item.electrodomestico.potenciaWatts}W)")

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
                            }, KeyboardType.Number)

                            Spacer(modifier = Modifier.height(8.dp))

                            InputField("Horas/día", horas, {
                                horas = it
                                viewModel.actualizarDatos(
                                    index,
                                    cantidad.toIntOrNull() ?: 1,
                                    it.toDoubleOrNull() ?: 1.0
                                )
                            }, KeyboardType.Number)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular el consumo total de forma manual
        Button(
            onClick = {
                consumoCalculado = viewModel.calcularConsumoTotal()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Consumo Estimado")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar resultado solo si se ha calculado
        if (consumoCalculado > 0.0) {
            // 🧮 Cálculo de paneles solares necesarios (1 panel = 300 kWh/mes)
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

            // Botón para guardar el cálculo directamente desde esta pantalla
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
}
