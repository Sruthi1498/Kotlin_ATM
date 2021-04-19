package com.database

import com.bank.Atm
import com.bank.Card
import java.lang.IndexOutOfBoundsException

class CardDb {

    private var cardDetails = arrayListOf(
        Card(1, 1, 1234567, 9876),
        Card(2, 2, 2344567, 8762),
        Card(3, 3, 3852614, 7654),
        Card(4, 4, 7658798, 6540)
    )

    fun findCard(number: Long): Card {

        try{
            cardDetails.filter { it.number == number }[0]
        }
        catch (e: IndexOutOfBoundsException)
        {
          println("Invalid card number")
          val o = Atm()
          o.start()
        }
        return cardDetails.filter { it.number == number }[0]
    }

}


