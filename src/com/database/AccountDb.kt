package com.database

import com.bank.Account

class AccountDb{
    private var accountDetails = mutableListOf(
        Account(1,1,12000.0),
        Account(2,2,5098.0),
        Account(3,3,60008.9),
        Account(4,4,78000.8)
    )

    fun findAcc(UserId: Number): Account? {
        return accountDetails.find {
            it.UserId == UserId
        }
    }
    fun balance(UserID: Int): Double
    {
        return accountDetails[getIndex(UserID)].balance
    }

    fun withdraw(userId: Int, amount:Double)
    {
        accountDetails[getIndex(userId)].balance -= amount
    }
    fun deposit(userId: Int, amount: Double)
    {
        accountDetails[getIndex(userId)].balance += amount
    }

    fun getIndex(UserId:Int):Int {
       return accountDetails.indexOf(accountDetails.find { it.UserId == UserId })

}
}

