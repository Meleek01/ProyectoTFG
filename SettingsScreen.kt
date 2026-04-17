package com.example.socialgameapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialgameapp.ui.theme.SocialGameAppTheme

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Ajustes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        ListItem(
            headlineContent = { Text("Notificaciones") },
            trailingContent = { Switch(checked = true, onCheckedChange = {}) }
        )
        ListItem(
            headlineContent = { Text("Modo Oscuro") },
            trailingContent = { Switch(checked = false, onCheckedChange = {}) }
        )
        ListItem(
            headlineContent = { Text("Privacidad") },
            supportingContent = { Text("Configura quién puede ver tu perfil") },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SocialGameAppTheme {
        SettingsScreen()
    }
}
