package com.bank

import com.database.AccountDb
import com.database.UserDb
import com.database.CardDb
import java.time.LocalDateTime
import java.util.NoSuchElementException

class Mini(private var amount:Double,private var desc: String, private var card: String)
{
    override fun toString(): String {
        return "$amount $desc $card"
    }
}

class Atm {
    private lateinit var card: Card
    private lateinit var account: Account
    private lateinit var user: User
    private val cardDb = CardDb()
    private val accountDb = AccountDb()
    private val userDb = UserDb()
    private val i = 0
    private var map = MultiMap<String, Mini>()

    fun start() {
        println("Enter your card number")
        val c = readLine()
        c.let {
            card = cardDb.findCard(c.toString())
            user = userDb.findDb(card.UserId)
            account = accountDb.findAcc(user.UserId)!!
            println("Welcome, ${user.name}")
            option()
        }
    }
    private fun option()
    {
        while (i == 0) {
            println("1.Check Balance \n2.Withdraw \n3.Deposit \n4.Transfer\n5.Mini Statement\n6.Exit\n")

            when (readLine()) {
                "1" -> checkBalance()
                "2" -> withdraw()
                "3" -> deposit()
                "4" -> transfer()
                "5" -> miniStatement()
                "6" -> start()
                }
            }
        }

    private fun deposit() {
        print("Enter amount to deposit:")
        val amt = readLine()

        if (amt != null) {
            if (amt.toInt() > 0 && pin()) {
                accountDb.deposit(account.UserId, amt.toDouble())
                println("$amt successfully deposited")
                val a = Mini(amt.toDouble(),"DEPOSITED FROM", card.number)
                map.put(card.number,a)
            } else {
                println("Enter a valid amount")
                deposit()
            }}}

    private fun withdraw()  {

        val amount = accountDb.balance(account.UserId)
        print("Enter amount to withdraw:")
        val amt = readLine()
        if (amt != null) {
            if (amount < amt.toDouble()) {
                println("Insufficient balance")
                option()
            }
            if (amt.toInt() > 0 && pin()) {
                accountDb.withdraw(account.UserId, amt.toDouble())
                println("$amt successfully withdrawn")
                println()
                val a = Mini(amt.toDouble(), "WITHDRAWN FROM", card.number)
                map.put(card.number, a)
            } else {
                println("Enter a valid amount")
                withdraw()
            }}}

    private fun checkBalance() {
        val balance = accountDb.balance(account.UserId)
        if(pin()){
            println("Balance: $balance")
            println()
        }
    }

    private fun transfer()
    {
        val amount = account.balance
        println("Enter the card no of account to transfer the amount: ")
        val cardNo = readLine()
        if(pin()) {
            println("Enter the amount to be transferred")
            val tAmt = readLine()
            if (tAmt != null) {
                if(amount>tAmt.toDouble()) {
                    val tCard = cardDb.findCard(cardNo.toString())
                    val tUser = userDb.findDb(tCard.UserId)
                    val tAccount = accountDb.findAcc(tUser.UserId)

                    accountDb.withdraw(account.UserId, tAmt.toDouble())
                    accountDb.deposit(tAccount!!.UserId, tAmt.toDouble())

                    println("Transferred from ${user.name} to ${tUser.name}")
                    println()
                    val a = Mini(tAmt.toDouble(),"TRANSFERRED TO", tUser.name)
                    val b = Mini(tAmt.toDouble(), "TRANSFERRED FROM", user.name)
                    map.put(card.number,a)
                    map.put(cardNo.toString(),b)
                } else {
                    println("Insufficient balance")
                    transfer()
                }}}}

    private fun pin(): Boolean{
        print("Enter pin: ")
        val pin = readLine()
        if(pin != null)
            if(card.pin == pin.toInt()){
                return true
            }
        println("Wrong pin")
        option()
        return false
    }
    private fun miniStatement()
    {
            println("----MINI STATEMENT----")
            println("DATE: ${LocalDateTime.now()}")
            println("NAME: ${user.name}")
            try {
                if(this.card.number == card.number)
                    println(map.values(card.number))
            }
            catch (e: NoSuchElementException)
            {
                println("No transactions yet")
                option()
            }
        }
    }
fun main()
{
    val o = Atm()
    o.start()
}