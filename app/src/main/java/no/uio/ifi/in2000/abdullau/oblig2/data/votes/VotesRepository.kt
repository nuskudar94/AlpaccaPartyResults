package no.uio.ifi.in2000.abdullau.oblig2.data.votes

class VotesRepository {

 /*
 class VotesRepository(
    private val individualVotesDataSource: IndividualVotesDataSource,
    private val aggregatedVotesDataSource: AggregatedVotesDataSource
) {

    suspend fun getVotesForPartyInDistrict(district: District, partyId: String): Int? {
        return when (district) {
            District.DISTRICT1 -> individualVotesDataSource.getVotesFromDistrict1().alpacaPartyId == partyId
            District.DISTRICT2 -> individualVotesDataSource.getVotesFromDistrict2().alpacaPartyId == partyId
            District.DISTRICT3 -> aggregatedVotesDataSource.getVotesFromDistrict3().alpacaPartyId == partyId
        }?.let {
            when (district) {
                District.DISTRICT1 -> individualVotesDataSource.getVotesFromDistrict1().numberOfVotesForParty
                District.DISTRICT2 -> individualVotesDataSource.getVotesFromDistrict2().numberOfVotesForParty
                District.DISTRICT3 -> aggregatedVotesDataSource.getVotesFromDistrict3().numberOfVotesForParty
            }
        }?.get(partyId)
    }
}
  */

    /*
        package no.uio.ifi.in2000.abdullau.oblig2.data.votes

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.PartyVotes

class VotesRepository(
    private val individualVotesDataSource: IndividualVotesDataSource,
    private val aggregatedVotesDataSource: AggregatedVotesDataSource
) {

    suspend fun getVotesForDistrict(district: District): List<PartyVotes> {
        return when (district) {
            District.DISTRICT1 -> getVotesFromIndividualDistrict(individualVotesDataSource::getVotesFromDistrict1)
            District.DISTRICT2 -> getVotesFromIndividualDistrict(individualVotesDataSource::getVotesFromDistrict2)
            District.DISTRICT3 -> getVotesFromAggregatedDistrict(aggregatedVotesDataSource::getVotesFromDistrict3)
        }
    }

    private suspend fun getVotesFromIndividualDistrict(getVotesFunction: suspend () -> DistrictVotes): List<PartyVotes> {
        val districtVotes = getVotesFunction()
        return listOf(
            PartyVotes(
                partyId = districtVotes.alpacaPartyId,
                votes = districtVotes.numberOfVotesForParty
            )
        )
    }

    private suspend fun getVotesFromAggregatedDistrict(getVotesFunction: suspend () -> DistrictVotes): List<PartyVotes> {
        val districtVotes = getVotesFunction()
        // Assuming that the AggregatedVotesDataSource returns votes for multiple parties in a single call
        // The DistrictVotes object should be adjusted to contain a list of PartyVotes instead of a single party
        // Here we assume that the DistrictVotes object already contains the necessary party data.
        return districtVotes.partyVotes
    }
}
     */


}