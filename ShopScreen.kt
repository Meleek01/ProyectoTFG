package com.example.socialgameapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialgameapp.ui.theme.SocialGameAppTheme

data class ShopItem(val id: Int, val name: String, val price: String, val description: String)

@Composable
fun ShopScreen() {
    val items = listOf(
        ShopItem(1, "Escudo de Hierro", "200 Oro", "Protección básica"),
        ShopItem(2, "Poción de Vida", "50 Oro", "Recupera 50 HP"),
        ShopItem(3, "Espada Rúnica", "500 Oro", "Daño mágico"),
        ShopItem(4, "Capa de Invisibilidad", "1000 Oro", "Pura facha")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Tienda de Objetos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemCard(item)
            }
        }
    }
}

@Composable
fun ItemCard(item: ShopItem) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = item.price, color = MaterialTheme.colorScheme.primary, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Comprar */ }, modifier = Modifier.fillMaxWidth()) {
                Text("COMPRAR")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    SocialGameAppTheme {
        ShopScreen()
    }
}
