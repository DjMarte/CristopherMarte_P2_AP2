package edu.ucne.cristophermarte_p2_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.cristophermarte_p2_ap2.presentation.navigation.RegistroNavHost
import edu.ucne.cristophermarte_p2_ap2.ui.theme.CristopherMarte_P2_AP2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CristopherMarte_P2_AP2Theme {
                RegistroNavHost(rememberNavController())
            }
        }
    }
}