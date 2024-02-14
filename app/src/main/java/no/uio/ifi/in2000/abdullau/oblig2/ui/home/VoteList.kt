import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Vote(val partyName: String, val votes: Int)

@Composable
fun VoteList(voteList: List<Vote>) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Heading for the table
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Parti",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Left

            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Stemmer",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Right

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // List of votes
        LazyColumn {
            items(voteList) { vote ->
                VoteRow(vote = vote)
            }
        }
    }
}

@Composable
fun VoteRow(vote: Vote) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = vote.partyName,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Left

        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${vote.votes}",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Right

        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVoteList() {
    val dummyData = listOf(
        Vote("Parti A", 150),
        Vote("Parti B", 250),
        Vote("Parti C", 100),
        Vote("Parti D", 300)
    )
    VoteList(voteList = dummyData)
}