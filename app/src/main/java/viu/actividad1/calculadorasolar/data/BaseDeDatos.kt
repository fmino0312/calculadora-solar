package viu.actividad1.calculadorasolar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Calculo::class, Electrodomestico::class], version = 1)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun calculoDao(): CalculoDao
    abstract fun electrodomesticoDao(): ElectrodomesticoDao

    companion object {
        @Volatile
        private var INSTANCIA: BaseDeDatos? = null

        fun obtenerInstancia(context: Context): BaseDeDatos {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "base_datos_solar"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
        suspend fun precargarElectrodomesticos(dao: ElectrodomesticoDao) {
            val lista = listOf(
                Electrodomestico(nombre = "Refrigerador", potenciaWatts = 150),
                Electrodomestico(nombre = "Lavadora", potenciaWatts = 500),
                Electrodomestico(nombre = "Microondas", potenciaWatts = 1200),
                Electrodomestico(nombre = "Aire acondicionado", potenciaWatts = 2000),
                Electrodomestico(nombre = "TV", potenciaWatts = 100)
            )
            dao.insertarTodos(lista)
        }

    }
}
