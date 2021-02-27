package account

import bank.Bank
import bank.id.AccountId
import bank.id.ClientId
import client.Client
import date.CustomDate

class CreditAccount private constructor(
    bank: Bank,
    owner: Client,
    initialAmount: Double
) : Account(bank, owner, initialAmount) {

    private val feeRate = bank.bankConfig.accountConfig.creditDailyFee
    private val limit = bank.bankConfig.accountConfig.creditLimit

    companion object Factory {
        fun ClientId.registerCreditAccount(initialAmount: Double = 0.00): AccountId =
            bank.registerAccount(CreditAccount(bank, this(), initialAmount))
    }

    override fun checkForceWithdraw(amount: Double) {
        require(amount > 0) { "Can't withdraw non-positive amount" }
        require(total - amount >= limit) { "Can't go past credit limit" }
    }

    override fun recalculateSavings(today: CustomDate) {
        if (total < 0) {
            accumulated += total * feeRate
        }
        super.recalculateSavings(today)
    }

}