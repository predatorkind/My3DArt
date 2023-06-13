package net.vertexgraphics.myportfolioapp.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.vertexgraphics.myportfolioapp.data.LocalEmailsDataProvider

class EmailClientViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(EmailClientUiState())
    val uiState: StateFlow<EmailClientUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val mailboxes :Map<MailboxType, List<Email>> =
            LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }
        _uiState.value = EmailClientUiState(mailboxes = mailboxes,
        currentSelectedEmail = mailboxes[MailboxType.Inbox]?.get(0)
            ?: LocalEmailsDataProvider.defaultEmail)
    }

    fun updateDetailsScreenState(email: Email) {
        _uiState.update { it.copy(
            currentSelectedEmail = email,
            isShowingHomepage = false,
        ) }
    }

    fun resetHomeScreenState() {
        _uiState.update {
            it.copy(
                currentSelectedEmail = it.mailboxes[it.currentMailbox]?.get(0)
                    ?: LocalEmailsDataProvider.defaultEmail,
                isShowingHomepage = true
            )
        }
    }

    fun updateCurrentMailbox(mailboxType: MailboxType) {
        _uiState.update {
            it.copy(
                currentMailbox = mailboxType
            )
        }
    }

}
