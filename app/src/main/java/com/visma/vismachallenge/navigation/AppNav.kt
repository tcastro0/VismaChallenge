package com.visma.vismachallenge.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.visma.expenses.presentation.screens.AddExpenseScreen
import com.visma.expenses.presentation.screens.ExpenseListScreen
import com.visma.expenses.presentation.viewmodel.AddExpenseViewModel
import com.visma.expenses.presentation.viewmodel.ExpenseListViewModel
import com.visma.photocapture.presentation.screens.PhotoCaptureScreen
import com.visma.photocapture.presentation.viewmodel.PhotoCaptureViewModel
import com.visma.photos.presentation.screens.PhotoDetailsScreen
import com.visma.photos.presentation.screens.PhotosScreen
import com.visma.photos.presentation.viewmodels.PhotoDetailsViewModel
import com.visma.photos.presentation.viewmodels.PhotosViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fab: ((@Composable () -> Unit)?) -> Unit
) {
    NavHost(
        navController,
        startDestination = Route.Dashboard.route,
        modifier = modifier
    ) {
        composable(
            Route.Dashboard.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            fab(null)
            Column {
                Text("Dashboard")
            }
        }
        composable(
            Route.Photos.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            val viewmodel: PhotosViewModel = hiltViewModel()
            PhotosScreen(
                viewmodel,
                onAddPhotoClick = {},
                fab = fab,
                onItemClick = { navController.navigate(Route.PhotoDetails.createRoute(it)) }
            )
        }

        composable(
            route = Route.PhotoDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val viewModel: PhotoDetailsViewModel = hiltViewModel()
            PhotoDetailsScreen(
                viewModel = viewModel,
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }



        composable(
            Route.ExpenseList.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            val expensesViewModel: ExpenseListViewModel = hiltViewModel()
            ExpenseListScreen(
                viewModel = expensesViewModel,
                onAddExpenseClick = { navController.navigate(Route.AddExpense.route) },
                fab = fab
            )
        }

        composable(
            route = Route.AddExpense.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            val addExpenseViewModel: AddExpenseViewModel = hiltViewModel()
            AddExpenseScreen(
                viewModel = addExpenseViewModel,
                onBackClick = { navController.popBackStack() },
                takePhoto = { navController.navigate(Route.PhotoCapture.route) },
                navController = navController
            )
            fab(null)
        }


        composable(
            route = Route.PhotoCapture.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            val photoViewModel: PhotoCaptureViewModel = hiltViewModel()

            PhotoCaptureScreen(
                photoViewModel,
                onBackClick = { uri, id ->
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("resultUri", uri.toString())
                        set("resultId", id)
                    }
                    navController.popBackStack()
                })
            fab(null)
        }
    }
}