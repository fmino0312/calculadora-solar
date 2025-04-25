package viu.actividad1.calculadorasolar.ui.screens.seleccion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import viu.actividad1.calculadorasolar.data.BaseDeDatos
import viu.actividad1.calculadorasolar.data.Calculo
import viu.actividad1.calculadorasolar.data.Electrodomestico
import viu.actividad1.calculadorasolar.model.ElectrodomesticoSeleccionado
import viu.actividad1.calculadorasolar.repository.RepositorioCalculos


class SeleccionViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = BaseDeDatos.obtenerInstancia(application).electrodomesticoDao()

    val listaDisponibles: StateFlow<List<Electrodomestico>> = dao.obtenerTodos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _seleccionados = MutableStateFlow<List<ElectrodomesticoSeleccionado>>(emptyList())
    val seleccionados: StateFlow<List<ElectrodomesticoSeleccionado>> = _seleccionados

    fun agregarAEleccion(electrodomestico: Electrodomestico) {
        val nuevaLista = _seleccionados.value.toMutableList()
        nuevaLista.add(ElectrodomesticoSeleccionado(electrodomestico))
        _seleccionados.value = nuevaLista
    }

    fun actualizarDatos(index: Int, cantidad: Int, horas: Double) {
        val nuevaLista = _seleccionados.value.toMutableList()
        val item = nuevaLista[index]
        nuevaLista[index] = item.copy(
            cantidad = cantidad,
            horasPorDia = horas
        )
        _seleccionados.value = nuevaLista
    }



    fun calcularConsumoTotal(): Double {
        return _seleccionados.value.sumOf { it.calcularConsumoMensual() }
    }

    fun limpiarSeleccion() {
        _seleccionados.value = emptyList()
    }

    init {
        viewModelScope.launch {
            dao.obtenerTodos().collect { lista ->
                if (lista.isEmpty()) {
                    precargarElectrodomesticos()
                }
            }
        }
    }
    fun guardarCalculoDesdeSeleccion(
        consumoMensual: Double,
        paneles: Int,
        energia: Double
    ) {
        val nuevo = Calculo(
            consumoMensual = consumoMensual,
            panelesRequeridos = paneles,
            energiaEstimada = energia
        )

        viewModelScope.launch {
            val dao = BaseDeDatos.obtenerInstancia(getApplication()).calculoDao()
            val repo = RepositorioCalculos(dao)
            repo.insertarCalculo(nuevo)
        }
    }

    fun agregarNuevoElectrodomestico(nombre: String, potencia: Double) {
        viewModelScope.launch {
            val nuevo = Electrodomestico(nombre = nombre, potenciaWatts = potencia.toInt())
            dao.insertar(nuevo)
        }
    }



    private suspend fun precargarElectrodomesticos() {
        val lista = listOf(
            Electrodomestico(nombre = "Refrigerador", potenciaWatts = 150),
            Electrodomestico(nombre = "Lavadora", potenciaWatts = 500),
            Electrodomestico(nombre = "Microondas", potenciaWatts = 1200),
            Electrodomestico(nombre = "Aire acondicionado", potenciaWatts = 2000),
            Electrodomestico(nombre = "Televisor", potenciaWatts = 100),
            Electrodomestico(nombre = "Ventilador", potenciaWatts = 70),
            Electrodomestico(nombre = "Ordenador", potenciaWatts = 300),
            Electrodomestico(nombre = "Horno eléctrico", potenciaWatts = 1800)
        )
        dao.insertarTodos(lista)
    }


}




