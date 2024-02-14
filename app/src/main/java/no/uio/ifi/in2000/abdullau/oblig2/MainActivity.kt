package no.uio.ifi.in2000.abdullau.oblig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party
import no.uio.ifi.in2000.abdullau.oblig2.ui.home.HomeScreen
import no.uio.ifi.in2000.abdullau.oblig2.ui.party.PartyScreen
import no.uio.ifi.in2000.abdullau.oblig2.ui.theme.Abdullau_oblig2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Abdullau_oblig2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(navController = navController) }
        composable("party_screen/{partyID}") { backStackEntry ->
            PartyScreen(
                partyID = backStackEntry.arguments?.getString("partyID").toString(),
                navController = navController
            )
        }
    }
}