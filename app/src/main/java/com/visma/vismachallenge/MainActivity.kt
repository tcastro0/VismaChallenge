package com.visma.vismachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.visma.vismachallenge.navigation.AppNavGraph
import com.visma.vismachallenge.ui.theme.VismaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var fabContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    val navController = rememberNavController()

    VismaChallengeTheme {
        Scaffold(
            floatingActionButton = {
                AnimatedVisibility (
                    visible = fabContent!=null,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it }
                ) { fabContent?.invoke() }
            },
        ) { innerPadding ->
            AppNavGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            ) { fabContent = it }

        }
    }
}
