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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.DistrictVotes
import okio.IOException

class IndividualVotesDataSource() {
    private val json = Json { ignoreUnknownKeys = true }
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(json)
        }
    }

    suspend fun getVotesFromDistrict1(): List<DistrictVotes> {
        val url =
            "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district1.json"

        val response: List<District1Response> = client.get(url).body()

        //val votesList: List<District1Response> = Json.decodeFromString(response)

        //val district1ResponseList: List<District1Response> = Json.decodeFromString(response)

        //val partiesVotes = district1ResponseList.groupBy { it.partyId }.mapValues { it.size }
        //val partiesVotes = response.votes.groupBy { it.key }.mapValues { it.value.sum() }

        val totalVotes = response.groupBy { it.id }.mapValues { it.value.size }

        // Transform the response to DistrictVotes format
        return totalVotes.map { (partyId, count) ->
            DistrictVotes(
                district = District.DISTRICT1,
                alpacaPartyId = partyId,
                numberOfVotesForParty = count
            )
        }
    }
    suspend fun getVotesFromDistrict2(): List<DistrictVotes> {
        val url = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district2.json"
        //val json = Json { ignoreUnknownKeys = true }
        val response: List<District2Response> = client.get(url).body()

        val totalVotes = response.groupBy { it.id }.mapValues { it.value.size }

        // Transform the response to DistrictVotes format
        return totalVotes.map { (partyId, count) ->
            DistrictVotes(
                district = District.DISTRICT2,
                alpacaPartyId = partyId,
                numberOfVotesForParty = count
            )
        }
    }

    // Define data classes to match the JSON structure from each endpoint
    @Serializable
    private data class District1Response(val id: String)
    @Serializable
    private data class District2Response(val id: String)

}