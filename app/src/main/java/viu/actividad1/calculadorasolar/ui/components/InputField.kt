package viu.actividad1.calculadorasolar.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions

//  Componente reutilizable para campos de entrada con borde (OutlinedTextField)
@Composable
fun InputField(
    label: String,                    // Lo que se muestra como etiqueta del campo
    value: String,                    // El valor actual que contiene
    onValueChange: (String) -> Unit,  // Qué hacer cuando cambia
    keyboardType: KeyboardType = KeyboardType.Text // Tipo de teclado
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },          // Etiqueta visible
        singleLine = true,                // Solo una línea
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible
    )
}
