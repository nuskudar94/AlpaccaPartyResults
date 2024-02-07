package no.uio.ifi.in2000.abdullau.oblig2.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = viewModel()){

    val partyInfoUiState by HomeScreenViewModel


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(parties) { partyInfo ->
            Text(
                text = partyInfo.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            LazyColumn {
                items(partyInfo.parties) { party ->
                    PartyCard(party)
                }
            }
        }
    }




}

@Composable
fun PartyCard(party: Party, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = party.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = party.leader,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = party.description,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun HomeScreen(
    parties: List<PartyInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(parties) { partyInfo ->
            Text(
                text = partyInfo.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            LazyColumn {
                items(partyInfo.parties) { party ->
                    PartyCard(party)
                }
            }
        }
    }
}