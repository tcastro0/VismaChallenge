package com.visma.vismachallenge.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.visma.vismachallenge.navigation.Route

@Composable
fun BottomMenu(navController: NavController) {
    val selectedItem = remember { mutableStateOf(Route.Dashboard.route) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard") },
            selected = selectedItem.value == Route.Dashboard.route,
            onClick = {
                navController.navigate(Route.Dashboard.route) {
                    popUpTo(Route.Dashboard.route) { inclusive = true }
                    launchSingleTop = true
                }
                selectedItem.value = Route.Dashboard.route
            },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBalanceWallet, contentDescription = "Expenses") },
            selected = selectedItem.value == Route.ExpenseList.route,
            onClick = {
                navController.navigate(Route.ExpenseList.route) {
                    popUpTo(Route.ExpenseList.route) { inclusive = true }
                    launchSingleTop = true
                }
                selectedItem.value = Route.ExpenseList.route
            },
            label = { Text("Expenses") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.PhotoLibrary, contentDescription = "Photos") },
            selected = selectedItem.value == Route.Photos.route,
            onClick = {
                navController.navigate(Route.Photos.route) {
                    popUpTo(Route.Photos.route) { inclusive = true }
                    launchSingleTop = true
                }
                selectedItem.value = Route.Photos.route
            },
            label = { Text("Photos") }
        )

    }

}