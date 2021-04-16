package com.database

import com.bank.User

class UserDb {
    private var userDetails = arrayListOf(
        User(1,"alice"),
        User(2,"bob"),
        User(3,"jack"),
        User(4,"jill")
    )

    fun findDb(UserId: Number): User
    {
        return userDetails.filter {
            it.UserId == UserId
        }[0]
    }
}