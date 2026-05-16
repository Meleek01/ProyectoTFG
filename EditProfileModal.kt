package com.fithero.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fithero.app.data.User
import com.fithero.app.ui.theme.*

@Composable
fun EditProfileModal(user: User, onSave: (String, String, String) -> Unit, onClose: () -> Unit) {
    var name by remember { mutableStateOf(user.name) }
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }

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
                Text("Editar Perfil", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(Modifier.height(20.dp))

                // Avatar
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Purple, PurpleLight))),
                        contentAlignment = Alignment.Center
                    ) { Text("🦸", fontSize = 36.sp) }
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(PurpleBg).padding(horizontal = 16.dp, vertical = 7.dp)
                    ) { Text("📷 Cambiar foto", color = Purple, fontSize = 13.sp, fontWeight = FontWeight.SemiBold) }
                }

                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    placeholder = { Text("Nombre completo", color = Gray400) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray200, focusedBorderColor = Purple),
                    singleLine = true
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = username, onValueChange = { username = it },
                    placeholder = { Text("@usuario", color = Gray400) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray200, focusedBorderColor = Purple),
                    singleLine = true
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    placeholder = { Text("Correo electrónico", color = Gray400) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray200, focusedBorderColor = Purple),
                    singleLine = true
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { onSave(name, username, email) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple)
                ) { Text("Guardar Cambios", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            }
        }
    }
}
