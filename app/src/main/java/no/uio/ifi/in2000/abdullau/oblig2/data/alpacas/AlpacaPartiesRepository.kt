package no.uio.ifi.in2000.abdullau.oblig2.data.alpacas


import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesDataSource
class AlpacaPartiesRepository() {

    private val dataSource = AlpacaPartiesDataSource()

    suspend fun getParties(): List<PartyInfo>{
        //val partyinfo: PartyInfo =
         return listOf(dataSource.fetchAlpacaParties())
        //return listOf(partyinfo)
    }
}