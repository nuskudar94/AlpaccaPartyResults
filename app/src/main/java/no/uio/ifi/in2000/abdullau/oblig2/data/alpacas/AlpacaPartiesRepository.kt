package no.uio.ifi.in2000.abdullau.oblig2.data.alpacas


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesDataSource
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party

class AlpacaPartiesRepository() {

    private val dataSource = AlpacaPartiesDataSource()

    private  val party =MutableStateFlow<List<Party>>(listOf())

    fun observeParty(): StateFlow<List<Party>> = party.asStateFlow()

    suspend fun getParties(): List<PartyInfo>{
        //val partyinfo: PartyInfo =
        delay(1000)
         return dataSource.fetchAlpacaParties()
        //return listOf(partyinfo)
    }

    suspend fun getParty(id: String): Party? {
        val parties = getParties().firstOrNull()
        return parties?.parties?.find { party -> party.id == id }
    }
}