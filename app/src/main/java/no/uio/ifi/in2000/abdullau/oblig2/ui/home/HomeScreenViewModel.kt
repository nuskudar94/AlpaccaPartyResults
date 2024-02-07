package no.uio.ifi.in2000.abdullau.oblig2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesDataSource
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.PartyInfo


data class PartyInfoUIState(

    val partyinfoes: List<PartyInfo> = listOf()
)

class HomeScreenViewModel: ViewModel() {

    private val repository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _partyInfoUIState = MutableStateFlow(PartyInfoUIState())
    val partyinfoUiState: StateFlow<PartyInfoUIState> = _partyInfoUIState.asStateFlow()

    init {
        viewModelScope.launch{
            _partyInfoUIState.update {
                it.copy(
                    partyinfoes = repository.getParties()
                )
            }
        }
    }

}