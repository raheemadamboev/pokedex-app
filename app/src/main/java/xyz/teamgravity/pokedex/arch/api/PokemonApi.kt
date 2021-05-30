package xyz.teamgravity.pokedex.arch.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.teamgravity.pokedex.model.PokemonListModel
import xyz.teamgravity.pokedex.model.PokemonModel

interface PokemonApi {
    companion object{
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val PAGE_SIZE = 20
    }

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListModel

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String
    ): PokemonModel
}