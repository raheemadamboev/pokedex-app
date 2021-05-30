package xyz.teamgravity.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import xyz.teamgravity.pokedex.arch.api.PokemonApi
import xyz.teamgravity.pokedex.arch.repository.PokemonRepository
import xyz.teamgravity.pokedex.arch.repository.PokemonRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun providePokemonRepository(pokemonApi: PokemonApi): PokemonRepository = PokemonRepositoryImpl(pokemonApi)
}