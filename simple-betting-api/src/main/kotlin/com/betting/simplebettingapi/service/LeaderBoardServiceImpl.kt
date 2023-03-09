package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.repository.WalletRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


@Service
class LeaderBoardServiceImpl(
    @Autowired private val walletRepository: WalletRepository,
    @Value("\${app.leaderboard.size}") private val leaderboardSize: Int
) : LeaderBoardService {
    private val logger = KotlinLogging.logger {}

    @Cacheable(value = ["leaderBoard"])
    override fun getLeaderBoard(): LeaderBoardDto {
        val walletModels =
            walletRepository.findAllByOrderByBalanceDesc(PageRequest.of(0, leaderboardSize))

        val leaderList = ArrayList<LeaderBoardDto.LeaderBoardEntry>()

        walletModels.forEach { wm ->
            leaderList.add(
                LeaderBoardDto.LeaderBoardEntry(
                    wm.account!!.username,
                    wm.balance.subtract(BigDecimal(1000))
                )
            )
        }

        return LeaderBoardDto(leaderList)
    }

    //refresh cache every x minutes
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