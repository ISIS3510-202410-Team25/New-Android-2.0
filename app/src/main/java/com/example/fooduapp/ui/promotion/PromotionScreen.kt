package com.example.fooduapp.ui.promotion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fooduapp.ui.navigation.NavigationDestination
import com.example.fooduapp.R
object PromotionDestination : NavigationDestination {
    override val route = "Promotion"
    override val titleRes = R.string.promotion_title
}
@Composable
fun PromotionScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Promotion Screen")
    }
}