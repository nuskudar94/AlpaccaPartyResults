package no.uio.ifi.in2000.abdullau.oblig2.ui.party

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyScreen(
    partyScreenViewModel: PartyViewModel = viewModel(),
    partyID: String,
    navController: NavController
) {
    val partyi by partyScreenViewModel.observeSelectedParty().collectAsState()

    LaunchedEffect(Unit) {
        partyScreenViewModel.loadPartybyID(partyID)
    }

    Log.d("PartyScreen", "Party state: $partyi")
    //val partyID = remember { partyScreenViewModel.get }

    //val colorValue = Color(android.graphics.Color.parseColor(partyi.partyList?.color))
    val partyColor = partyi?.color
    val colorValue = if (partyColor != null) {
        Color(android.graphics.Color.parseColor(partyColor))
    } else {
        // Provide a default color if partyColor is null
        Color(android.graphics.Color.parseColor("#FFFFFF")) // white color as an example
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ) ,
            title = { Text("Party Info", onTextLayout = {})
                        },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${partyi?.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            onTextLayout = {}
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = partyi?.img,
            contentDescription = "Party banner",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Leader: ${partyi?.leader}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            onTextLayout = {}
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = colorValue
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = " ${partyi?.description}",
            fontSize = 14.sp,
            onTextLayout = {}
        )

    }

}