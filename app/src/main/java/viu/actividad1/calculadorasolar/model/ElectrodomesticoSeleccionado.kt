package viu.actividad1.calculadorasolar.model

import viu.actividad1.calculadorasolar.data.Electrodomestico

// Esta clase se usa para representar una selección del usuario
data class ElectrodomesticoSeleccionado(
    val electrodomestico: Electrodomestico,
    var cantidad: Int = 1,
    var horasPorDia: Double = 1.0
) {
    // Calcula el consumo mensual estimado en kWh
    fun calcularConsumoMensual(): Double {
        val potenciaKW = electrodomestico.potenciaWatts / 1000.0
        return potenciaKW * horasPorDia * 30 * cantidad
    }
}
