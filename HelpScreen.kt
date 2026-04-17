package com.example.socialgameapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialgameapp.ui.theme.SocialGameAppTheme

@Composable
fun HelpScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Ayuda y Soporte", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                HelpItem("¿Cómo gano XP?", "Cumpliendo misiones diarias. Cada misión tiene una recompensa específica en XP y Oro.")
            }
            item {
                HelpItem("¿Para qué sirve el Oro?", "Puedes usarlo en la tienda para comprar equipamiento y pociones que te ayudarán en tu aventura.")
            }
            item {
                HelpItem("Contactar Soporte", "Si tienes algún problema técnico, escríbenos a soporte@socialgame.com")
            }
        }
    }
}

@Composable
fun HelpItem(title: String, description: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpScreenPreview() {
    SocialGameAppTheme {
        HelpScreen()
    }
}
