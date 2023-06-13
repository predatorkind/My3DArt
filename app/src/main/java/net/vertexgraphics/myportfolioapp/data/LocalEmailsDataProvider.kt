package net.vertexgraphics.myportfolioapp.data

import net.vertexgraphics.myportfolioapp.model.Email

object LocalEmailsDataProvider {
    val allEmails = listOf(
        Email(
            id= 0L,
            sender = LocalAccountsDataProvider.getContactAccountById(2),
            recipients = listOf(LocalAccountsDataProvider.userAccount),
            subject = "Car",
            body = "I have a car to sell. \n Interested? \n\n Regards",
            createdAt = 1672341234567L
        ),
        Email(
            id= 1L,
            sender = LocalAccountsDataProvider.getContactAccountById(5),
            recipients = listOf(LocalAccountsDataProvider.userAccount),
            subject = "Shopping Frenzy",
            body = "Hello! \nBuy five pairs of socks and get one free. " +
                    "Click link to take advantage of this promotion  \n\n Regards",
            createdAt = 1692221264567L
        ),
        Email(
            id= 2L,
            sender = LocalAccountsDataProvider.getContactAccountById(7),
            recipients = listOf(LocalAccountsDataProvider.userAccount,
                LocalAccountsDataProvider.getContactAccountById(8)),
            subject = "Party tomorrow?",
            body = "Hello! \nWanna come by for a couple of drinks tomorrow? \n " +
                    "Let me know asap? \n\n Bye",
            createdAt = 1692221237567L
        ),
        Email(
            id= 3L,
            sender = LocalAccountsDataProvider.getContactAccountById(3),
            recipients = listOf(LocalAccountsDataProvider.userAccount),
            subject = "Sad news",
            body = "Dear John! \nI am afraid we have to declare bankruptcy.. " +
                    "There is nothing we can do anymore. The account is empty.  \n\n Regards",
            createdAt = 1682221294567L
        ),
        Email(
            id= 4L,
            sender = LocalAccountsDataProvider.getContactAccountById(6),
            recipients = listOf(LocalAccountsDataProvider.userAccount),
            subject = "Congratulations!",
            body = "Hi! \nYou have won a prize! \n " +
                    "Follow this link to claim it.  \n\n Regards",
            createdAt = 1682221234567L
        ),
    )

    fun get(id: Long): Email? {
        return allEmails.firstOrNull() {
            it.id == id
        }
    }

    val defaultEmail = Email(id=-1, sender=LocalAccountsDataProvider.defaultAccount)
}