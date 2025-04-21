package viu.actividad1.calculadorasolar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculos")
data class Calculo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val consumoMensual: Double,            // kWh ingresado por el usuario
    val panelesRequeridos: Int,            // Paneles calculados
    val energiaEstimada: Double,           // kWh estimados por los paneles
    val fecha: Long = System.currentTimeMillis()  // Timestamp para historial
)
