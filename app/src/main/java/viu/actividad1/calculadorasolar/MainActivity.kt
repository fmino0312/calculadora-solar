package viu.actividad1.calculadorasolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import viu.actividad1.calculadorasolar.ui.navigation.NavegacionApp
import viu.actividad1.calculadorasolar.ui.navigation.Rutas
import viu.actividad1.calculadorasolar.ui.screens.historial.HistorialViewModel
import viu.actividad1.calculadorasolar.ui.screens.inicio.InicioViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Modifier
import viu.actividad1.calculadorasolar.ui.screens.seleccion.SeleccionViewModel
import androidx.compose.material.icons.filled.Settings



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lo que se hace: Se crean los ViewModels manualmente (usando application)
        val inicioViewModel = InicioViewModel(application)
        val historialViewModel = HistorialViewModel(application)
        val seleccionViewModel = SeleccionViewModel(application)

        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = {
                    BottomAppBar {
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(Rutas.SELECCION) },
                            label = { Text("Electrodomésticos") },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(Rutas.INICIO) },
                            label = { Text("Inicio") },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(Rutas.HISTORIAL) },
                            label = { Text("Historial") },
                            icon = { Icon(Icons.Default.List, contentDescription = null) }
                        )
                    }

                }
            ) { padding -> // 👈 renombramos 'it' a 'padding' para claridad
                // Lo que se hace: Aplicamos el padding del Scaffold al NavHost
                Surface(modifier = Modifier.padding(padding)) {
                    NavegacionApp(
                        navController = navController,
                        inicioViewModel = inicioViewModel,
                        historialViewModel = historialViewModel,
                        seleccionViewModel = seleccionViewModel
                    )
                }
            }

        }
    }
}
