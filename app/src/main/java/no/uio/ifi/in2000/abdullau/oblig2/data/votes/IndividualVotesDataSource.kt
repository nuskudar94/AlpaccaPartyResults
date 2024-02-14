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

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
    }

    suspend fun getVotesFromDistrict1(): DistrictVotes {
        val url = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppagver/district1.json"
        val json = Json { ignoreUnknownKeys = true }
        val response: District1Response = client.get(url){
            parameter("key", "value" )
        }.bodyAsText().let { json.decodeFromString(it) }

        //val votesList: List<District1Response> = Json.decodeFromString(response)

        //val district1ResponseList: List<District1Response> = Json.decodeFromString(response)

        //val partiesVotes = district1ResponseList.groupBy { it.partyId }.mapValues { it.size }


        // Transform the response to DistrictVotes format
        return DistrictVotes(
            district = District.DISTRICT1,
            alpacaPartyId = response.votes.first().keys.first(),
            numberOfVotesForParty = response.votes.sumOf { it.values.sum() }
        )
    }

    suspend fun getVotesFromDistrict2(): DistrictVotes {
        val url = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppagver/district2.json"
        val json = Json { ignoreUnknownKeys = true }
        val response: District2Response = client.get(url){
            parameter("key", "value" )
        }.bodyAsText().let { json.decodeFromString(it) }

        // Transform the response to DistrictVotes format
        return DistrictVotes(
            district = District.DISTRICT2,
            alpacaPartyId = response.votes.first().keys.first(),
            numberOfVotesForParty = response.votes.sumOf { it.values.sum() }
        )
    }

    // Define data classes to match the JSON structure from each endpoint
    @Serializable
    private data class District1Response(val votes: List<Map<String, Int>>)
    @Serializable
    private data class District2Response(val votes: List<Map<String, Int>>)

}