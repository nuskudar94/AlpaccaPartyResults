package no.uio.ifi.in2000.abdullau.oblig2.model.alpacas

import kotlinx.serialization.Serializable

@Serializable
data class PartyInfo(val parties: List<Party>)

@Serializable
data class Party(val id: String, val name: String, val leader: String, val img: String, val color: String, val description: String)

