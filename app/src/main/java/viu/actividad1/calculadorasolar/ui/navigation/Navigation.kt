package viu.actividad1.calculadorasolar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import viu.actividad1.calculadorasolar.ui.screens.historial.HistorialScreen
import viu.actividad1.calculadorasolar.ui.screens.historial.HistorialViewModel
import viu.actividad1.calculadorasolar.ui.screens.inicio.InicioScreen
import viu.actividad1.calculadorasolar.ui.screens.inicio.InicioViewModel
import viu.actividad1.calculadorasolar.ui.screens.seleccion.SeleccionScreen
import viu.actividad1.calculadorasolar.ui.screens.seleccion.SeleccionViewModel


// Definimos rutas como constantes para evitar errores de texto
object Rutas {
    const val INICIO = "inicio"
    const val HISTORIAL = "historial"
    const val SELECCION = "seleccion"
}

// Lo que se hace: Este NavHost contiene la navegación entre pantallas
@Composable
fun NavegacionApp(
    navController: NavHostController,
    inicioViewModel: InicioViewModel,
    historialViewModel: HistorialViewModel,
    seleccionViewModel: SeleccionViewModel
) {
    NavHost(navController = navController, startDestination = Rutas.INICIO) {
        composable(Rutas.INICIO) {
            InicioScreen()
        }
        composable(Rutas.HISTORIAL) {
            HistorialScreen(historialViewModel)
        }
        composable(Rutas.SELECCION) {
            SeleccionScreen(viewModel = seleccionViewModel) { consumoEstimado ->
                inicioViewModel.calcular(consumoEstimado) //
                navController.navigate(Rutas.INICIO)
            }
            }

        }
}


