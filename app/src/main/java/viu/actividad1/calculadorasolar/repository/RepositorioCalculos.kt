package viu.actividad1.calculadorasolar.repository

import kotlinx.coroutines.flow.Flow
import viu.actividad1.calculadorasolar.data.Calculo
import viu.actividad1.calculadorasolar.data.CalculoDao

class RepositorioCalculos(private val calculoDao: CalculoDao) {

    val historial: Flow<List<Calculo>> = calculoDao.obtenerHistorial()

    suspend fun insertarCalculo(calculo: Calculo) {
        calculoDao.insertar(calculo)
    }

    suspend fun eliminarCalculo(calculo: Calculo) {
        calculoDao.eliminar(calculo)
    }
}
