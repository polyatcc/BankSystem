package account.config

import date.CustomDate
import java.util.*

class AccountConfig(
    debitInterestPercent: Double,
    creditFeePercent: Double,
    val creditLimit: Double,
    stagesPercents: Map<Double, Double>,
    private val lastStagePercents: Double
) {

    companion object {
        private val lengthOfYear
            get() = CustomDate.nowProvider().yearLength()
    }

    val debitYearInterest = debitInterestPercent / 100

    val debitDailyInterest: Double
        get() = debitYearInterest / lengthOfYear

    val creditYearFee = creditFeePercent / 100

    val creditDailyFee: Double
        get() = creditYearFee / lengthOfYear

    private val stagesPercents = TreeMap(stagesPercents)

    val depositYearInterest
        get(): (Double) -> Double = { deposit ->
            (stagesPercents.ceilingEntry(deposit)?.value ?: lastStagePercents) / 100
        }

    val depositDailyInterest
        get(): (Double) -> Double = { deposit ->
            depositYearInterest(deposit) / lengthOfYear
        }

}