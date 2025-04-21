package viu.actividad1.calculadorasolar.ui.screens.inicio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import viu.actividad1.calculadorasolar.data.BaseDeDatos
import viu.actividad1.calculadorasolar.data.Calculo
import viu.actividad1.calculadorasolar.repository.RepositorioCalculos

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = BaseDeDatos.obtenerInstancia(application).calculoDao()
    private val repositorio = RepositorioCalculos(dao)

    private val _panelesNecesarios = MutableStateFlow(0)
    val panelesNecesarios: StateFlow<Int> = _panelesNecesarios

    private val _energiaEstimadaykwh = MutableStateFlow(0.0)
    val energiaEstimadaykwh: StateFlow<Double> = _energiaEstimadaykwh

    fun calcular(consumoMensual: Double) {
        // Lógica base: cada panel genera 300 kWh/mes
        val energiaPorPanel = 300.0
        val paneles = (consumoMensual / energiaPorPanel).toInt().coerceAtLeast(1)
        val energia = paneles * energiaPorPanel

        _panelesNecesarios.value = paneles
        _energiaEstimadaykwh.value = energia
    }

    fun guardarCalculo(consumoMensual: Double) {
        val calculo = Calculo(
            consumoMensual = consumoMensual,
            panelesRequeridos = _panelesNecesarios.value,
            energiaEstimada = _energiaEstimadaykwh.value
        )

        viewModelScope.launch {
            repositorio.insertarCalculo(calculo)
        }
    }
}
