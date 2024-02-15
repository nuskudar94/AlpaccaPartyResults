@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package no.uio.ifi.in2000.abdullau.oblig2.ui.home

import Vote
import VoteList
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text as Material3Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = viewModel(),
                navController: NavController
){
    val districts = listOf(District.DISTRICT1, District.DISTRICT2, District.DISTRICT3)
    var selectedDistrict by remember { mutableStateOf(District.DISTRICT1)}
    val partyInfo by homeScreenViewModel.partyinfo.collectAsState()
    val voteInfo by homeScreenViewModel.voteinfo.collectAsState()

    Log.i("voteInfo", voteInfo.toString())

    // Update the votes when a district is selected
    LaunchedEffect(selectedDistrict) {
        voteInfo
    }
    Column {
        DistrictDropdown(selectedDistrict = selectedDistrict) { newSelection ->
            selectedDistrict = newSelection
        }
        // Component that displays votes
        VoteList(voteList = voteInfo.voteinfoes.map { Vote(it.key, it.value) })
        LazyColumn {
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

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Material3Text(text = "Select District:")
        Spacer(modifier = Modifier.width(8.dp))
        Box {
            Material3Text(text = selectedDistrict.name, modifier = Modifier.clickable { expanded = true })
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                District.values().forEach { district ->
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

