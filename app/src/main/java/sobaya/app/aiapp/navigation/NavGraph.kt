import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object DogList : Screen("dogList")
    object DogDetail : Screen("dogDetail/{imageUrl}") {
        fun createRoute(imageUrl: String) = "dogDetail/${imageUrl}"
    }
}

@Composable
fun DogNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.DogList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.DogList.route) {
            DogListScreen(
                onDogSelected = { imageUrl ->
                    navController.navigate(Screen.DogDetail.createRoute(imageUrl))
                }
            )
        }
        
        composable(
            route = Screen.DogDetail.route,
            arguments = listOf(navArgument("imageUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: return@composable
            DogDetailScreen(
                imageUrl = imageUrl,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
} 