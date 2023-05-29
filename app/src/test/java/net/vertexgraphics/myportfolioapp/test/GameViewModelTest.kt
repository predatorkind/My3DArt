package net.vertexgraphics.myportfolioapp.test

import net.vertexgraphics.myportfolioapp.data.MAX_NO_OF_WORDS
import net.vertexgraphics.myportfolioapp.data.SCORE_INCREASE
import net.vertexgraphics.myportfolioapp.data.getUnscrambledWord
import net.vertexgraphics.myportfolioapp.model.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private  val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        // assert that after a correct answer the score changes
        // and isGuessedWordWrong flag is not triggered
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(10, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "and"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value

        // assert that after wrong answer the score does not change
        // and isGuessedWordWrong flag is triggered
        assertEquals(0, currentGameUiState.score)
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameVieModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // assert that after initialization ui state is reset
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        assertTrue(gameUiState.currentWordCount == 1)
        assertTrue(gameUiState.score == 0)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // assert that after each correct answer the score is updated correctly
            assertEquals(expectedScore, currentGameUiState.score)
        }

        // assert that after all questions are answered the current word count is updated
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // assert that after 10 questions are answered the game is over
        assertTrue(currentGameUiState.isGameOver)




    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedWordCountIncreased() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount
        val lastScore = currentGameUiState.score
        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value

        // assert that score remains unchanged after word is skipped
        assertEquals(lastScore, currentGameUiState.score)

        // assert that word count is increased by 1 after word is skipped
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }
}