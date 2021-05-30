package xyz.teamgravity.pokedex.arch.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.pokedex.arch.api.PokemonApi
import xyz.teamgravity.pokedex.arch.repository.PokemonRepository
import xyz.teamgravity.pokedex.helper.util.Resource
import xyz.teamgravity.pokedex.model.PokedexModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokedexModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
        println("debug: load $currentPage")
    }

    /**
     * Calculates the dominate color
     */
    fun calculateDominateColor(image: Bitmap, onFinish: (Color) -> Unit) {
        val bitmap = image.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemons(currentPage * PokemonApi.PAGE_SIZE, PokemonApi.PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PokemonApi.PAGE_SIZE >= result.data!!.count
                    val pokedexResult = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }

                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

                        PokedexModel(
                            name = entry.name.capitalize(Locale.ROOT),
                            imageUrl = url,
                            number = number.toInt()
                        )
                    }

                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexResult
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                    println("debug: ${result.message}")
                }
            }
        }
    }
}