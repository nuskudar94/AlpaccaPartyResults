package no.uio.ifi.in2000.abdullau.oblig2.ui.party

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.abdullau.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.abdullau.oblig2.model.alpacas.Party

/*
data class PartyUIState(
    val partyList: Party? = null
)
*/
class PartyViewModel: ViewModel() {

    private val repository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _party = MutableStateFlow<Party?>(null)
    fun observeSelectedParty(): StateFlow<Party?> = _party



    fun loadPartybyID(partyId: String){
        viewModelScope.launch{
                val party1 = repository.getParty(partyId)
                _party.value = party1
        }
    }

}