package tech.mujtaba.entain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import tech.mujtaba.entain.core.navigation.NavGraphProvider
import tech.mujtaba.entain.core.ui.theme.EntainTheme
import tech.mujtaba.entain.feature.race.navigation.UpComingRacesDestination

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphProviders: Set<@JvmSuppressWildcards NavGraphProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EntainTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = UpComingRacesDestination.route
                ) {
                    navGraphProviders.forEach {
                        it.graph(this)
                    }
                }
            }
        }
    }
}
