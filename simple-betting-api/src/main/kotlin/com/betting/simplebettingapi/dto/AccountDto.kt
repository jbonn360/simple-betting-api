package com.betting.simplebettingapi.dto

class AccountDto(val id: Int = -1, username: String, name: String, surname: String, wallet: WalletDto?) {
    val username = username

    val name = name

    val surname = surname

    val wallet = wallet
}