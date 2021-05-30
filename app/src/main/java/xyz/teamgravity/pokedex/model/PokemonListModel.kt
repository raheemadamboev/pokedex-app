package xyz.teamgravity.pokedex.model

data class PokemonListModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultModel>
)