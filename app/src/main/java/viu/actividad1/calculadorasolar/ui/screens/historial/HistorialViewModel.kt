package viu.actividad1.calculadorasolar.ui.screens.historial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import viu.actividad1.calculadorasolar.data.BaseDeDatos
import viu.actividad1.calculadorasolar.data.Calculo
import viu.actividad1.calculadorasolar.repository.RepositorioCalculos

// Este ViewModel proporciona acceso al historial de cálculos guardados y permite eliminarlos
class HistorialViewModel(application: Application) : AndroidViewModel(application) {

    // Se obtiene el DAO y el repositorio desde la base de datos
    private val dao = BaseDeDatos.obtenerInstancia(application).calculoDao()
    private val repositorio = RepositorioCalculos(dao)

    //  Se expone el historial como StateFlow para observar los cambios
    val historial: StateFlow<List<Calculo>> = repositorio.historial
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    //  Se permite eliminar un cálculo desde la pantalla
    fun eliminar(calculo: Calculo) {
        viewModelScope.launch {
            repositorio.eliminarCalculo(calculo)
        }
    }
}
