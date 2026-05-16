package com.fithero.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fithero.app.data.Mission
import com.fithero.app.data.User
import com.fithero.app.ui.theme.*

@Composable
fun HomeScreen(user: User, missions: List<Mission>, onMissionClick: (Mission) -> Unit) {
    val xpPct = if (user.xpNext > 0) (user.xp.toFloat() / user.xpNext).coerceIn(0f, 1f) else 0f
    val completedCount = missions.count { it.status == "completed" }

    LazyColumn(modifier = Modifier.fillMaxSize().background(Background)) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(Purple, PurpleDark)))
                    .padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 28.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text("Nivel de Jugador", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text(user.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text("Nivel ${user.level}", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Experiencia (XP)", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        Text("${user.xp}/${user.xpNext} XP", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { xpPct },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(10.dp)),
                        color = Color(0xFFA78BFA),
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        StatItem("Misiones", completedCount.toString())
                        StatItem("Logins", user.logins.toString())
                        StatItem("Monedas", user.coins.toString())
                    }
                }
            }
        }

        item {
            Text("Misiones de hoy", modifier = Modifier.padding(16.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Gray900)
        }

        items(missions) { mission ->
            MissionCard(mission = mission, onClick = { onMissionClick(mission) })
        }
        
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Text(label, color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
    }
}

@Composable
fun MissionCard(mission: Mission, onClick: () -> Unit) {
    val pct = if (mission.goal > 0) (mission.progress.toFloat() / mission.goal).coerceIn(0f, 1f) else 0f
    val color = parseColor(mission.colorHex)

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(mission.icon, fontSize = 24.sp, modifier = Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(mission.title, fontWeight = FontWeight.Bold, color = Gray900)
                Text(mission.desc, fontSize = 12.sp, color = Gray500)
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { pct },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = color,
                    trackColor = PurpleBg
                )
            }
            Text("+${mission.xpReward} XP", modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    FitHeroTheme {
        HomeScreen(
            user = User(name = "Entrenador", level = 10, xp = 500, xpNext = 2000, coins = 300, logins = 5),
            missions = listOf(
                Mission(1, "Correr 5km", "Cardio", "🏃", "#F59E0B", goal = 5, progress = 2, unit = "km", xpReward = 500, coinReward = 50, category = "cardio"),
                Mission(2, "Sentadillas", "Fuerza", "💪", "#7C3AED", goal = 50, progress = 50, status = "completed", unit = "reps", xpReward = 300, coinReward = 30, category = "fuerza")
            ),
            onMissionClick = {}
        )
    }
}
