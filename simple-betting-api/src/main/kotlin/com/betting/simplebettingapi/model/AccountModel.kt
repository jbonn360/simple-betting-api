package com.betting.simplebettingapi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AccountModel(username: String?, name: String?, surname: String?, var balance: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = -1;

    val username = username;

    val name = name;

    val surname = surname;
}