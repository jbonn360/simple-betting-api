package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@Service
class LeaderBoardServiceImpl(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val betRepository: BetRepository,
    @Value("\${app.leaderboard.size}") private val leaderboardSize: Int
) : LeaderBoardService{
    private val logger = KotlinLogging.logger {}

    @Cacheable(value = ["leaderBoard"])
    override fun getLeaderBoard(): Mono<LeaderBoardDto> {
        // get a list of accounts
        val accounts = accountRepository.findAll()

        //calculate total winnings for each account
        val accountWinnings = HashMap<AccountModel, Mono<BigDecimal>>()

        accounts.forEach {
            val betsWon = betRepository.findAllByAccount(it).filter {
                BetStatus.isWinningStatus(it.status)
            }
            val totalWon = betsWon.sumOf {
                Utils.calculatePrize(it.amount, it.status)
            }
            accountWinnings[it] = Mono.just(totalWon)
        }

        //sort list of wallets by total winnings
        val sortedAccounts = accountWinnings.toList().sortedByDescending { (_, value) ->
            value.block()
        }

        // return first x accounts
        val sortedAccountTruncated: List<LeaderBoardDto.LeaderBoardEntry> =
            sortedAccounts.take(leaderboardSize).map {
                LeaderBoardDto.LeaderBoardEntry(it.first.username, it.second.block()!!)
            }

        return Mono.just(LeaderBoardDto(sortedAccountTruncated))
    }

    @CacheEvict(value = ["leaderBoard"], allEntries = true)
    @Scheduled(
        fixedRateString = "\${app.cache.leaderboard.lifetime-mins}",
        initialDelayString = "\${app.cache.leaderboard.lifetime-mins}",
        timeUnit = TimeUnit.MINUTES
    )
    fun refreshLeaderBoardCache() {
        logger.info { "Refreshing leaderboard cache" }
    }
}