package no.uio.ifi.in2000.abdullau.oblig2.data.votes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.DistrictVotes

class AggregatedVotesDataSource {

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
    }


    suspend fun getVotesFromDistrict3(): List<DistrictVotes> {
        val url = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district3.json"
        val json = Json { ignoreUnknownKeys = true }
        val response: District3Response = client.get(url).body()



        // Transform the response to DistrictVotes format
        return response.parties.map{ partyVote ->
            DistrictVotes(
            district = District.DISTRICT3,
            alpacaPartyId = partyVote.partyId,
            numberOfVotesForParty = partyVote.votes
        )
    }

        }
    @Serializable
    data class District3Response(
        val parties: List<District3Response.PartyVote>
    ) {
        @Serializable
        data class PartyVote(
            val partyId: String,
            val votes: Int
        )
    }
}