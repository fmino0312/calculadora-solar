package viu.actividad1.calculadorasolar.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculoDao {

    @Query("SELECT * FROM calculos ORDER BY fecha DESC")
    fun obtenerHistorial(): Flow<List<Calculo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(calculo: Calculo)

    @Delete
    suspend fun eliminar(calculo: Calculo)
}
