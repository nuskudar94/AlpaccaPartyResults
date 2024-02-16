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

    private val _selectedDistrict = MutableStateFlow<District?>(null)
    val selectedDistrict: StateFlow<District?> = _selectedDistrict.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadPartyVotes(district: District) {
        viewModelScope.launch {
            try {
                val votesMap=repository.getPartiesWithVotes(district)
                _voteInfo.update {
                        currentState-> currentState.copy(
                    voteinfoes = votesMap)
            }
            }   catch (e: Exception){
                Log.e("LoadPartyVotes", "Hata olustu ${e.message}")
                _errorMessage.value = "There is a problem !"
            }
        }
    }
    fun selectDistrict(district: District) {
        _selectedDistrict.value = district
        loadPartyVotes(district)
    }

    init {
        viewModelScope.launch{
            try {
                _partyInfo.update { partyinfoUiState ->
                    partyinfoUiState.copy(
                        partyinfoes = repository.getParties()
                    )
                }
            } catch (e: Exception){
                Log.e("LoadPartyVotes", "Hata olustu ${e.message}")
                _errorMessage.value = "There is a problem!"
            }

        }

    }

}