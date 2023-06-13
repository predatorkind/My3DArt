package net.vertexgraphics.myportfolioapp.data

import net.vertexgraphics.myportfolioapp.R
import net.vertexgraphics.myportfolioapp.model.Account

object LocalAccountsDataProvider {
    val defaultAccount = Account(-1, "", "", "", R.drawable.a1)
    val userAccount = Account(
        1,"John","Doe","john.doe@gmail.com", R.drawable.a1)

    private val userContacts = listOf(
        Account(
            2, "Tom", "Taylor", "tom.taylor@gmail.com", R.drawable.a2
        ),
        Account(
            3, "Sarah", "Smith", "sarah.smith@gmail.com", R.drawable.a3
        ),
        Account(
            4, "Jessica", "Jones", "jessica.jones@gmail.com", R.drawable.a4
        ),
        Account(
            5, "David", "Davidson", "david.davidson@gmail.com", R.drawable.a5
        ),
        Account(
            6, "Jack", "Jackson", "jack.jackson@gmail.com", R.drawable.a6
        ),
        Account(
            7, "Ann", "Anderson", "ann.anderson@gmail.com", R.drawable.a7
        ),
        Account(
            8, "Kate", "Kite", "kate.kite@gmail.com", R.drawable.a8
        )
    )

    fun getContactAccountById(accountId: Long): Account {
        return userContacts.firstOrNull() {
            it.id == accountId
        } ?: userContacts.first()
    }

}
