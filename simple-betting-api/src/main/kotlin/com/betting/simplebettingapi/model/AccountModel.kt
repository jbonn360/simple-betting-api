package com.betting.simplebettingapi.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.UniqueConstraint

@Entity
class AccountModel(username: String, name: String, surname: String, balance: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1;

    @Column(unique = true)
    val username = username

    val name = name

    val surname = surname

    var balance = balance
}