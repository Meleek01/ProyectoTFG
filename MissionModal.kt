package com.fithero.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fithero.app.data.Mission
import com.fithero.app.ui.theme.*

@Composable
fun MissionModal(mission: Mission, onClose: () -> Unit, onUpdate: (Int, Int) -> Unit, onDelete: (Int) -> Unit) {
    val pct = (mission.progress.toFloat() / (mission.goal.takeIf { it > 0 } ?: 1)).coerceIn(0f, 1f)
    val color = parseColor(mission.colorHex)
    val isCompleted = mission.status == "completed"

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).clickable(onClick = onClose),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable(enabled = false) {},
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 36.dp)) {
                Box(modifier = Modifier.width(40.dp).height(4.dp).clip(RoundedCornerShape(4.dp)).background(Gray200).align(Alignment.CenterHorizontally))
                Spacer(Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Box(
                        modifier = Modifier.size(52.dp).clip(RoundedCornerShape(16.dp)).background(color.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) { Text(mission.icon, fontSize = 26.sp) }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(mission.title, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Gray900)
                        Text(mission.desc, fontSize = 13.sp, color = Gray500)
                    }
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                            .background(if (isCompleted) Color(0xFFD1FAE5) else PurpleBg)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(if (isCompleted) "✓ Completada" else "Activa", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                            color = if (isCompleted) Color(0xFF059669) else Purple)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Progreso", fontSize = 13.sp, color = Gray500, fontWeight = FontWeight.SemiBold)
                            Text("${mission.progress}/${mission.goal} ${mission.unit}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
                        }
                        Spacer(Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(10.dp)).background(PurpleBg)) {
                            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(pct).clip(RoundedCornerShape(10.dp)).background(color))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("${(pct * 100).toInt()}% completado", fontSize = 12.sp, color = Gray400)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Recompensa XP" to "+${mission.xpReward} ⚡", "Monedas" to "+${mission.coinReward} 🪙").forEach { (label, value) ->
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED))
                        ) {
                            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(label, fontSize = 11.sp, color = Gray400)
                                Text(value, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Amber)
                            }
                        }
                    }
                }

                if (!isCompleted) {
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onUpdate(mission.id, -1) },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Gray100, contentColor = Gray700)
                        ) { Text("−", fontSize = 20.sp) }
                        Button(
                            onClick = { onUpdate(mission.id, 1) },
                            modifier = Modifier.weight(2f).height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = color)
                        ) { Text("+ Registrar progreso", fontWeight = FontWeight.Bold, fontSize = 15.sp) }
                    }
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { onDelete(mission.id) },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF2F2), contentColor = Red)
                ) { Text("🗑️ Eliminar misión", fontWeight = FontWeight.SemiBold, fontSize = 14.sp) }
            }
        }
    }
}
