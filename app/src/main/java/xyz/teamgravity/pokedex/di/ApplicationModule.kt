package xyz.teamgravity.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.teamgravity.pokedex.arch.api.PokemonApi
import xyz.teamgravity.pokedex.arch.repository.PokemonRepository
import xyz.teamgravity.pokedex.arch.repository.PokemonRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PokemonApi.BASE_URL)
            .build()
            .create(PokemonApi::class.java)

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonApi: PokemonApi): PokemonRepository = PokemonRepositoryImpl(pokemonApi)
}