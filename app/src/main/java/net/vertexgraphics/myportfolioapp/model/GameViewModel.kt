package net.vertexgraphics.myportfolioapp.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.vertexgraphics.myportfolioapp.data.words
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.update
import net.vertexgraphics.myportfolioapp.data.SCORE_INCREMENT


class GameViewModel: ViewModel() {
    //Game UI state
    private val _uiState = MutableStateFlow(GameUiState())

    //Backing property to avoid state updates from other classes
    val uiState: StateFlow<GameUiState>
        get() = _uiState.asStateFlow()

    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    private fun pickRandomWordAndShuffle(): String {
        currentWord = words.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(word)){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessWord: String){
        userGuess = guessWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)){
            val updatedScore = _uiState.value.score.plus(SCORE_INCREMENT)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState -> currentState.copy(isGuessedWordWrong = true) }
        }
        // Reset user guess
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        _uiState.update {
                currentState -> currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                )
        }
    }

    init {
        resetGame()
    }



}