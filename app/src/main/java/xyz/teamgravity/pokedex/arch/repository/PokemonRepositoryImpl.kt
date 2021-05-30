package xyz.teamgravity.pokedex.arch.repository

import xyz.teamgravity.pokedex.arch.api.PokemonApi
import xyz.teamgravity.pokedex.helper.util.Resource
import xyz.teamgravity.pokedex.model.PokemonListModel
import xyz.teamgravity.pokedex.model.PokemonModel

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override suspend fun getPokemons(offset: Int, limit: Int): Resource<PokemonListModel> {
        val response = try {
            api.getPokemons(offset, limit)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error happened")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemon(name: String): Resource<PokemonModel> {
        val response = try {
            api.getPokemon(name)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error happened")
        }
        return Resource.Success(response)
    }
}