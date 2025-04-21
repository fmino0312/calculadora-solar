package viu.actividad1.calculadorasolar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viu.actividad1.calculadorasolar.data.Calculo
import java.text.SimpleDateFormat
import java.util.*

// Componente reutilizable que se usa en la pantalla de historial
// Lo que se hace: Muestra los datos de un cálculo en una tarjeta
@Composable
fun TarjetaHistorial(calculo: Calculo, onClickEliminar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Lo que se hace: Se muestran los datos básicos del cálculo
            Text("Consumo: ${calculo.consumoMensual} kWh")
            Text("Paneles: ${calculo.panelesRequeridos}")
            Text("Energía estimada: ${calculo.energiaEstimada} kWh")
            Text("Fecha: ${formatearFecha(calculo.fecha)}", style = MaterialTheme.typography.labelSmall)

            Spacer(modifier = Modifier.height(8.dp))

            // Lo que se hace: Botón para eliminar el cálculo
            Button(onClick = onClickEliminar) {
                Text("Eliminar")
            }
        }
    }
}

// Lo que se hace: Convierte el timestamp a una fecha legible (dd/MM/yyyy HH:mm)
fun formatearFecha(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
