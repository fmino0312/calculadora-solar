package viu.actividad1.calculadorasolar.ui.screens.inicio

import SeccionContenido
import SeccionTitulo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SeccionTitulo(
            titulo = "☀️ CALCULADORA SOLAR",
            descripcion = "Descubre cuántos paneles solares necesitas para cubrir el consumo de tu hogar."
        )

        SeccionContenido(
            titulo = "🧭 ¿CÓMO FUNCIONA?",
            contenido = """
1️⃣ Selecciona tus electrodomésticos y cuántas horas al día los usas.

2️⃣ Calculamos tu consumo total mensual.

3️⃣ Te mostramos cuántos paneles solares necesitas (1 panel = 300 kWh/mes).

4️⃣ Guarda tus cálculos para consultarlos después.
            """.trimIndent(),
            backgroundColorHex = 0xFFE1BEE7 // morado pastel
        )

        SeccionContenido(
            titulo = "📐 FÓRMULAS USADAS",
            contenido = """
✅ Consumo mensual (kWh) =  
(potencia en W / 1000) × horas por día × 30 × cantidad

✅ Paneles necesarios =  
consumo mensual total / 300
            """.trimIndent(),
            backgroundColorHex = 0xFFCE93D8 // lavanda suave
        )

        SeccionContenido(
            titulo = "💾 FUNCIONALIDADES",
            contenido = """
🔹 Guarda tus cálculos.

🔹 Consulta tu historial.

🔹 Calcula tu consumo energético fácilmente.

🔹 Optimiza el uso de energía solar en tu hogar.
            """.trimIndent(),
            backgroundColorHex = 0xFFBA68C8 // morado más fuerte
        )
    }
}
