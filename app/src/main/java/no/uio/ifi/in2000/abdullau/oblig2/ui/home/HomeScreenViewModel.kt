package no.uio.ifi.in2000.abdullau.oblig2.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.abdullau.oblig2.model.votes.District


data class PartyInfoUIState(

    val partyinfoes: List<PartyInfo> = listOf()
)

data class VotesInfoUIState(

    val voteinfoes: Map<String,Int> = mapOf()
)

class HomeScreenViewModel: ViewModel() {

    private val repository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _partyInfo = MutableStateFlow(PartyInfoUIState())
    val partyinfo: StateFlow<PartyInfoUIState> = _partyInfo.asStateFlow()

    private val _voteInfo = MutableStateFlow(VotesInfoUIState())
    val voteinfo : StateFlow<VotesInfoUIState> = _voteInfo.asStateFlow()

    init {
        val districts = listOf(District.DISTRICT1, District.DISTRICT2, District.DISTRICT3)
        viewModelScope.launch{
            _partyInfo.update { partyinfoUiState ->
                partyinfoUiState.copy(
                    partyinfoes = repository.getParties()

                )
            }
            _voteInfo.update {votesInfoUiState ->
                val voteinfoes = mutableMapOf<String, Int>()
                for (district in districts) {
                    voteinfoes.putAll(repository.getPartiesWithVotes(district = district))
                }
                votesInfoUiState.copy(
                    voteinfoes = voteinfoes

                )
            }
        }

    }

}