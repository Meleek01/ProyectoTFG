package com.example.socialgameapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialgameapp.ui.theme.SocialGameAppTheme
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Missions : Screen("missions", "Misiones", Icons.AutoMirrored.Filled.List)
    object Shop : Screen("shop", "Tienda", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
    object Settings : Screen("settings", "Ajustes", Icons.Default.Settings)
    object Help : Screen("help", "Ayuda", Icons.AutoMirrored.Filled.Help)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    val mainItems = listOf(Screen.Missions, Screen.Shop, Screen.Profile)
    val secondaryItems = listOf(Screen.Settings, Screen.Help)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Menú Principal",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                
                // Items Principales
                mainItems.forEach { screen ->
                    NavigationDrawerItem(
                        label = { Text(screen.title) },
                        selected = false,
                        onClick = {
                            navController.navigate(screen.route)
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(screen.icon, contentDescription = null) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Items Secundarios
                secondaryItems.forEach { screen ->
                    NavigationDrawerItem(
                        label = { Text(screen.title) },
                        selected = false,
                        onClick = {
                            navController.navigate(screen.route)
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(screen.icon, contentDescription = null) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val title = (mainItems + secondaryItems).find { it.route == currentRoute }?.title ?: "Social Game"
                        Text(title, fontWeight = FontWeight.Bold) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    },
                    actions = {
                        var showMenu by remember { mutableStateOf(false) }
                        
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ajustes") },
                                onClick = { 
                                    navController.navigate(Screen.Settings.route)
                                    showMenu = false 
                                },
                                leadingIcon = { Icon(Icons.Default.Settings, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Ayuda") },
                                onClick = { 
                                    navController.navigate(Screen.Help.route)
                                    showMenu = false 
                                },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Help, null) }
                            )
                            HorizontalDivider()
                            DropdownMenuItem(
                                text = { Text("Cerrar Sesión") },
                                onClick = { 
                                    onLogout()
                                    showMenu = false 
                                },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Logout, null) }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    mainItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController, startDestination = Screen.Missions.route) {
                    composable(Screen.Missions.route) { MissionsScreen() }
                    composable(Screen.Shop.route) { ShopScreen() }
                    composable(Screen.Profile.route) { ProfileScreen() }
                    composable(Screen.Settings.route) { SettingsScreen() }
                    composable(Screen.Help.route) { HelpScreen() }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SocialGameAppTheme {
        HomeScreen(onLogout = {})
    }
}
