package com.example.socialgameapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialgameapp.ui.theme.SocialGameAppTheme

data class Mission(val id: Int, val title: String, val reward: String, val isCompleted: Boolean)

@Composable
fun MissionsScreen() {
    // Estado para las misiones para que sea interactivo
    var missions by remember { mutableStateOf(listOf(
        Mission(1, "Caminar 5km", "50 XP", false),
        Mission(2, "Beber 2L de agua", "20 XP", true),
        Mission(3, "Leer 10 páginas", "30 XP", false),
        Mission(4, "Completar TFG", "1000 XP", false)
    ))}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Misiones Diarias",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(missions) { mission ->
                MissionItem(
                    mission = mission,
                    onToggle = { id ->
                        missions = missions.map { 
                            if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it 
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MissionItem(mission: Mission, onToggle: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(mission.id) },
        colors = CardDefaults.cardColors(
            containerColor = if (mission.isCompleted) 
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (mission.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (mission.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = mission.title, 
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (mission.isCompleted) TextDecoration.LineThrough else null
                )
                Text(text = "Recompensa: ${mission.reward}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MissionsScreenPreview() {
    SocialGameAppTheme {
        MissionsScreen()
    }
}
