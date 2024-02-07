package no.uio.ifi.in2000.abdullau.oblig2.data.alpacas
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo
import kotlinx.coroutines.*
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party

class AlpacaPartiesDataSource {



    suspend fun fetchAlpacaParties(): PartyInfo = withContext(Dispatchers.IO) {
            val client = HttpClient(CIO)
            val httpResponse: HttpResponse = client.get("https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json")
            //client.get<List<Party>>("https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json")

            val PartiesInfo: PartyInfo = httpResponse.body()

        return@withContext PartiesInfo
    }
    /*
    fun main() = runBlocking {
        val alpacaParties = fetchAlpacaParties()
        alpacaParties.parties.forEach { party ->
            println("Party ID: ${party.id}, Party Name: ${party.name}, Party Leader: ${party.leader}, Party Image: ${party.img}, Party Color: ${party.color}, Description: ${party.description}")
        }

     */
    }
