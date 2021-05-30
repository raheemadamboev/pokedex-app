package xyz.teamgravity.pokedex.arch.repository

import xyz.teamgravity.pokedex.helper.util.Resource
import xyz.teamgravity.pokedex.model.PokemonListModel
import xyz.teamgravity.pokedex.model.PokemonModel

interface PokemonRepository {

    suspend fun getPokemons(offset: Int, limit: Int): Resource<PokemonListModel>

    suspend fun getPokemon(name: String): Resource<PokemonModel>
}