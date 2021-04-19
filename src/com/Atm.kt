package com

import com.bank.Account
import com.bank.Card
import com.bank.User
import com.database.AccountDb
import com.database.UserDb
import com.database.CardDb
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.NoSuchElementException

data class Mini(private var amount:Double,private var type: String,private var desc: String, private var card: String)
{
    override fun toString(): String {

        return "$ $amount   $type   $desc $card"
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
    private var map = MultiMap<Long, Mini>()

    fun start() {
        println("Enter your card number")
        val c = readLine()
        c.let {
            card = cardDb.findCard(c!!.toLong())
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
                val a = Mini(amt.toDouble(),"DEPOSIT","Deposited to", card.number.toString())
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
                val a = Mini(amt.toDouble(),"WITHDRAW", "Withdrawn from", card.number.toString())
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
                    val tCard = cardDb.findCard(cardNo!!.toLong())
                    val tUser = userDb.findDb(tCard.UserId)
                    val tAccount = accountDb.findAcc(tUser.UserId)

                    accountDb.withdraw(account.UserId, tAmt.toDouble())
                    accountDb.deposit(tAccount!!.UserId, tAmt.toDouble())

                    println("Transferred from ${user.name} to ${tUser.name}")
                    println()
                    val a = Mini(tAmt.toDouble(),"TRANSFER","Transferred to", tUser.name)
                    val b = Mini(tAmt.toDouble(),"TRANSFER", "Transferred from", user.name)
                    map.put(card.number,a)
                    map.put(cardNo.toLong(),b)
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
            println("------------MINI STATEMENT------------")
        println()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)

        println("Date and Time: $formatted")
            println("NAME: ${user.name}")
        println()
        println("AMOUNT    TYPE       DESC")
        println("---------------------------------------------")
            try {
                    map.values(card.number)
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