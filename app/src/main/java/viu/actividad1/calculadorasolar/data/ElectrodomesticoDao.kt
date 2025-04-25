package viu.actividad1.calculadorasolar.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectrodomesticoDao {

    @Query("SELECT * FROM electrodomesticos")
    fun obtenerTodos(): Flow<List<Electrodomestico>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodos(electrodomesticos: List<Electrodomestico>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(electrodomestico: Electrodomestico)

    @Query("DELETE FROM electrodomesticos")
    suspend fun eliminarTodos()

    @Delete
    suspend fun eliminar(electrodomestico: Electrodomestico)

    @Update
    suspend fun actualizar(electrodomestico: Electrodomestico)

}
