package com.fithero.app.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fithero.app.ui.theme.*

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    var faceId by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    if (loading) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(900)
            onLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF1a1035), Color(0xFF2d1b69), Color(0xFF0f2460))
                )
            )
            .padding(28.dp)
    ) {
        // Decorative circles
        repeat(4) { i ->
            val size = (180 + i * 80).dp
            Box(
                modifier = Modifier
                    .size(size)
                    .align(Alignment.TopEnd)
                    .offset(x = (60 + i * 30).dp, y = (-40 - i * 30).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.04f))
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text("Cancelar", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)

            Spacer(Modifier.height(20.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFFA855F7), Purple))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💪", fontSize = 30.sp)
                }
                Spacer(Modifier.height(16.dp))
                Text("Bienvenido", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(6.dp))
                Text("Ingresa tus credenciales para continuar", color = Color.White.copy(alpha = 0.55f), fontSize = 14.sp)
            }

            Spacer(Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(20.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text("Usuario o Correo", color = Gray400) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Gray200,
                            focusedBorderColor = Purple,
                            unfocusedContainerColor = Color(0xFFF9FAFB),
                            focusedContainerColor = Color(0xFFF9FAFB)
                        ),
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Contraseña", color = Gray400) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { showPass = !showPass }) {
                                Icon(
                                    if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = Gray400
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Gray200,
                            focusedBorderColor = Purple,
                            unfocusedContainerColor = Color(0xFFF9FAFB),
                            focusedContainerColor = Color(0xFFF9FAFB)
                        ),
                        singleLine = true
                    )

                    if (error.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Text(error, color = Red, fontSize = 13.sp, modifier = Modifier.fillMaxWidth())
                    }

                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (username.isBlank() || password.isBlank()) {
                                error = "Por favor rellena todos los campos"
                            } else {
                                error = ""
                                loading = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple),
                        enabled = !loading
                    ) {
                        Text(if (loading) "Iniciando..." else "Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Spacer(Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Habilitar Face ID?", fontSize = 14.sp, color = Gray700)
                        // Toggle switch
                        val thumbOffset by animateDpAsState(if (faceId) 20.dp else 0.dp, label = "thumb")
                        Box(
                            modifier = Modifier
                                .width(44.dp).height(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (faceId) Purple else Gray200)
                                .clickable { faceId = !faceId }
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(x = thumbOffset)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    Text(
                        "¿Olvidaste tu contraseña?",
                        color = Purple,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
