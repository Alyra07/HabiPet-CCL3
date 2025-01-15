package at.ccl3.habipet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.R

@Composable
fun HeaderWithLogo(
    headingText: String,
    navController: NavController?,
    showBackButton: Boolean = false
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (showBackButton) {
            // Back button icon only if showBackButton is true
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(28.dp)
                    .clickable { navController?.popBackStack() }
            )
        } else {
            // Logo Icon if showBackButton is false
            Image(
                painter = painterResource(id = R.drawable.logo_notext),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        // headingText
        Text(text = headingText, style = MaterialTheme.typography.headlineSmall)
    }
}