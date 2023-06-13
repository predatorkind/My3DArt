package net.vertexgraphics.myportfolioapp.model

import net.vertexgraphics.myportfolioapp.data.LocalEmailsDataProvider

data class EmailClientUiState (
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomepage: Boolean = true,

        ){
    val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }

}
