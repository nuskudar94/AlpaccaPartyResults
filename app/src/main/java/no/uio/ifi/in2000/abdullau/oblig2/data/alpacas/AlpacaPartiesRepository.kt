package no.uio.ifi.in2000.abdullau.oblig2.data.alpacas


import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesDataSource
import no.uio.ifi.in2000.abdullau.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District

class AlpacaPartiesRepository() {
    private val votesRepository = VotesRepository()
    private val dataSource = AlpacaPartiesDataSource()

    private  val party =MutableStateFlow<List<Party>>(listOf())

    //fun observeParty(): StateFlow<List<Party>> = party.asStateFlow()

    suspend fun getParties(): List<PartyInfo>{
        //val partyinfo: PartyInfo =
        //delay(1000)
         return dataSource.fetchAlpacaParties()
        //return listOf(partyinfo)
    }

    suspend fun getParty(id: String): Party? {
        val parties = getParties()
        return parties.flatMap { it.parties }.find { it.id == id }
    }

    // New function to get parties with their respective vote counts for a specific district
    suspend fun getPartiesWithVotes(district: District): Map<String,Int> {
        val parties = getParties().flatMap { it.parties }
        val votesMap = mutableMapOf<String, Int>()
        val votes = votesRepository.getVotesForPartyInDistrict(district,"1")
        Log.i("Parties",parties.toString())
        Log.i("Votes: ", votes.toString())
         parties.forEach { party ->
             val votes = votesRepository.getVotesForPartyInDistrict(district,"1")
             votesMap[party.name] = votes
            }
        return votesMap.toMap()
        }
    }
