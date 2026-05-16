package com.fithero.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.fithero.app.data.ShopItem
import com.fithero.app.data.User
import com.fithero.app.ui.theme.*

@Composable
fun ShopScreen(user: User, onBuy: (ShopItem) -> Unit) {
    var selectedTab by remember { mutableStateOf("Equipamiento") }
    val tabs = listOf("Equipamiento", "Suplementos", "Especiales")

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        // Header
        Surface(shadowElevation = 2.dp, color = Color.White) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Tienda", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Gray900)
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Amber).padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("🪙 ${user.coins} Monedas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tabs.forEach { tab ->
                        Button(
                            onClick = { selectedTab = tab },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedTab == tab) Purple else Gray100,
                                contentColor = if (selectedTab == tab) Color.White else Gray500
                            )
                        ) {
                            Text(tab, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        val filtered = com.fithero.app.data.SHOP_ITEMS.filter { it.category == selectedTab }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                // Featured banner
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.linearGradient(listOf(Purple, PurpleLight)))
                        .padding(16.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Red).padding(horizontal = 8.dp, vertical = 2.dp)) {
                                Text("HOT", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                            }
                            Spacer(Modifier.height(6.dp))
                            Text("OFERTA ESPECIAL", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text("¡20% de descuento esta semana!", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        }
                        Text("💪", fontSize = 40.sp)
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Productos", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Gray900)
                    Text("${filtered.size} artículos", fontSize = 12.sp, color = Gray400)
                }
            }

            // Grid using chunked pairs
            items(filtered.chunked(2)) { row ->
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { item ->
                        ShopItemCard(item = item, user = user, onBuy = onBuy, modifier = Modifier.weight(1f))
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }

            item { Spacer(Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun ShopItemCard(item: ShopItem, user: User, onBuy: (ShopItem) -> Unit, modifier: Modifier = Modifier) {
    val canAfford = user.coins >= item.price
    val tagColor = when (item.tag) {
        "NUEVO" -> Green; "SALE" -> Red; "OFERTA" -> Amber; "TOP" -> Purple; else -> Purple
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFFF3F4F6), Color(0xFFE5E7EB)))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.emoji, fontSize = 44.sp)
                }
                item.tag?.let { tag ->
                    Box(
                        modifier = Modifier.align(Alignment.TopEnd).padding(6.dp)
                            .clip(RoundedCornerShape(8.dp)).background(tagColor).padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(tag, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(item.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Gray900, lineHeight = 16.sp)
            Text(item.brand, fontSize = 11.sp, color = Gray400)
            Spacer(Modifier.height(4.dp))
            Text("🪙 ${item.price}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Amber)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { onBuy(item) },
                enabled = canAfford,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    disabledContainerColor = Gray200,
                    disabledContentColor = Gray400
                )
            ) {
                Text(if (canAfford) "Comprar" else "Sin monedas", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
