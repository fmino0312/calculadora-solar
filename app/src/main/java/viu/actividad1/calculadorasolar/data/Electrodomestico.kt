package viu.actividad1.calculadorasolar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Representa un electrodoméstico con su nombre y consumo (en watts)
@Entity(tableName = "electrodomesticos")
data class Electrodomestico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val potenciaWatts: Int // por ejemplo 1500 W
)
