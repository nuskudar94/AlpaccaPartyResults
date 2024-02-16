@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package no.uio.ifi.in2000.abdullau.oblig2.ui.home

import androidx.compose.runtime.livedata.observeAsState
import Vote
import VoteList
import VoteRow
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Text as Material3Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District

@Composable
fun networkStatusFlow(context: Context): Flow<Boolean> {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = viewModel(),
                navController: NavController
) {
    val context = LocalContext.current
    val districts = listOf(District.DISTRICT1, District.DISTRICT2, District.DISTRICT3)
    var selectedDistrict by remember { mutableStateOf(District.DISTRICT1) }
    val partyInfo by homeScreenViewModel.partyinfo.collectAsState()
    val voteInfo by homeScreenViewModel.voteinfo.collectAsState()
    //val selectedDistrict by homeScreenViewModel.selectedDistrict.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    //val isNetworkAvailable by networkStatusViewModel.networkStatus.observeAsState(initial = true)

    val errorMessage by homeScreenViewModel.errorMessage.collectAsState()
    val isNetworkAvailable by networkStatusFlow(context).collectAsState(initial = true)
    /*
    LaunchedEffect(isNetworkAvailable) {
        if (!isNetworkAvailable) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("No internet connection")
            }
        }
    }

     */

    Log.i("voteInfo", voteInfo.toString())
    LaunchedEffect(selectedDistrict) {
        homeScreenViewModel.selectDistrict(selectedDistrict)


    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Material3Text("Info") },
                icon = { Icon(Icons.Filled.Info, contentDescription = "") },
                onClick = {

                    Log.i("IsNetworkAvailable", isNetworkAvailable.toString())
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = "No internet connection")

                    }

                }
            )
        }
    ) { contentPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize() // This ensures the Column takes up the available space
        ) {
            DistrictDropdown(selectedDistrict = selectedDistrict) { newSelection ->
                selectedDistrict = newSelection
            }
            // Component that displays votes
            //VoteList(voteList = voteInfo.voteinfoes.map { Vote(it.key, it.value) })
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // This makes LazyColumn take up the remaining space
            ) {
                items(voteInfo.voteinfoes.map {
                    Vote(it.key, it.value)
                }) { vote ->
                    VoteRow(vote = vote)
                }

                items(partyInfo.partyinfoes) { partyInfo ->
                    partyInfo.parties.forEach { party ->
                        PartyCard(party = Party(
                            party.id,
                            party.name,
                            party.leader,
                            party.img,
                            party.color,
                            party.description
                        ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = {
                                navController.navigate("party_screen/${party.id}")
                                Log.d(
                                    "PartyScreen",
                                    "Navigating to PartyScreen with partyID: ${party.id}"
                                )
                            })
                    }
                }
                item {
                    ErrorMessage(errorMessage = errorMessage)
                }

            }

        }

    }

}

/*
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

 */

@Composable
fun ErrorMessage(errorMessage: String?){
    if(!errorMessage.isNullOrEmpty()){
        Spacer(modifier = Modifier.padding(150.dp))
        OutlinedCard(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(start = 50.dp, end = 50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))

            }

        }
    }
}

@Composable
fun PartyCard(
    party: Party,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val colorValue = Color(android.graphics.Color.parseColor(party.color))

    Card(
        modifier = modifier
            .defaultMinSize(minHeight = 120.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = party.img,
                contentDescription = "Party leader",
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart
            )
            Spacer(modifier = Modifier.height(8.dp))
            Material3Text(
                text ="Party Name: ${party.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.DarkGray,
                onTextLayout = {}
            )
            Material3Text(
                text = "Leader: ${party.leader}",
                fontSize = 14.sp,
                color = Color.Black,
                onTextLayout = {}
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = colorValue
                )
        )
    }
}


// Function to create the dropdown menu
@Composable
fun DistrictDropdown(
    selectedDistrict: District,
    onSelectionChanged: (District) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val districts = listOf(District.DISTRICT1, District.DISTRICT2, District.DISTRICT3)

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Material3Text(text = "Select District:")
        Spacer(modifier = Modifier.width(8.dp))
        Box {
            Material3Text(text = selectedDistrict.name,
                modifier = Modifier.clickable { expanded = true })
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                districts.forEach { district ->
                    DropdownMenuItem(onClick = {
                        onSelectionChanged(district)
                        expanded = false
                    }, text = {
                        Material3Text(text = district.name)
                    })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPartyCard(party: Party) {
    Surface {
        PartyCard(party = Party(party.id,party.name,party.leader,party.img,party.color,party.description), modifier = Modifier)

    }
}

