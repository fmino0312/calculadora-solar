package viu.actividad1.calculadorasolar.ui.screens.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viu.actividad1.calculadorasolar.ui.components.TarjetaHistorial

// Esta pantalla muestra todos los cálculos guardados en una lista
@Composable
fun HistorialScreen(viewModel: HistorialViewModel) {
    // Lo que se hace: Observa el StateFlow del historial y se actualiza en tiempo real
    val listaHistorial by viewModel.historial.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Historial de Cálculos", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Lo que se hace: Muestra cada cálculo usando el componente reutilizable
        LazyColumn {
            items(listaHistorial) { calculo ->
                TarjetaHistorial(calculo = calculo) {
                    viewModel.eliminar(calculo) // Elimina el ítem al pulsar el botón
                }
            }
        }
    }
}
