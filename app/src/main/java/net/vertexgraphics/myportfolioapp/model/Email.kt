package net.vertexgraphics.myportfolioapp.model

data class Email(
    val id: Long,
    val sender: Account,
    val recipients: List<Account> = emptyList(),
    val subject: String = "",
    val body: String = "",
    var mailbox: MailboxType = MailboxType.Inbox,
    var createdAt: Long = -1

)
