package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.Instant

class LeaderBoardServiceTests {

    private val accountRepository: AccountRepository = mockk()
    private val betRepository: BetRepository = mockk()

    private val leaderBoardSize = 2

    private val leaderBoardService =
        LeaderBoardServiceImpl(accountRepository, betRepository, leaderBoardSize)

    private val accountModel1 = AccountModel(
        "username1",
        "name1",
        "surname1",
        WalletModel(
            BigDecimal(1000)
        )
    )

    private val betModel1 = BetModel(
        BigDecimal(55),
        5,
        BetStatus.WON_10,
        Instant.now(),
        mockk(),
        accountModel1
    )

    private val betModel2 = BetModel(
        BigDecimal(400),
        5,
        BetStatus.WON_5,
        Instant.now(),
        mockk(),
        accountModel1
    )

    private val accountModel2 = AccountModel(
        "username2",
        "name2",
        "surname2",
        WalletModel(
            BigDecimal(1000)
        )
    )

    private val betModel3 = BetModel(
        BigDecimal(55),
        5,
        BetStatus.LOST,
        Instant.now(),
        mockk(),
        accountModel2
    )

    private val betModel4 = BetModel(
        BigDecimal(100),
        5,
        BetStatus.WON_10,
        Instant.now(),
        mockk(),
        accountModel2
    )

    private val accountModel3 = AccountModel(
        "username3",
        "name3",
        "surname3",
        WalletModel(
            BigDecimal(1000)
        )
    )

    private val betModel5 = BetModel(
        BigDecimal(100),
        5,
        BetStatus.LOST,
        Instant.now(),
        mockk(),
        accountModel3
    )

    @Test
    fun givenAccountsAndBets_WhenGetLeaderBoardIsCalled_ThenLeaderBoardIsReturned(){
        every{ accountRepository.findAll() } returns listOf(accountModel1, accountModel2, accountModel3)
        every{ betRepository.findAllByAccount(accountModel1) } returns listOf(betModel1, betModel2)
        every{ betRepository.findAllByAccount(accountModel2) } returns listOf(betModel3, betModel4)
        every{ betRepository.findAllByAccount(accountModel3) } returns listOf(betModel5)

        val expected = LeaderBoardDto(
            listOf(
                LeaderBoardDto.LeaderBoardEntry(
                    "username1", BigDecimal(2550)
                ),
                LeaderBoardDto.LeaderBoardEntry(
                    "username2", BigDecimal(1000)
                ),
            )
        )

        //when
        val leaderBoard = leaderBoardService.getLeaderBoard()

        //then
        assertEquals(expected.leaders.size, leaderBoard.block()!!.leaders.size)
        assertEquals(expected.leaders[0].username, leaderBoard.block()!!.leaders[0].username)
        assertEquals(expected.leaders[0].winnings, leaderBoard.block()!!.leaders[0].winnings)
        assertEquals(expected.leaders[1].username, leaderBoard.block()!!.leaders[1].username)
        assertEquals(expected.leaders[1].winnings, leaderBoard.block()!!.leaders[1].winnings)
    }
}