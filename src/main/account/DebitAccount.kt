package account

import bank.Bank
import bank.id.AccountId
import bank.id.ClientId
import client.Client
import date.CustomDate

class DebitAccount private constructor(
    bank: Bank,
    owner: Client,
    initialAmount: Double
) : Account(bank, owner, initialAmount) {

    private val interestRate = bank.bankConfig.accountConfig.debitDailyInterest

    companion object Factory {
        fun ClientId.registerDebitAccount(initialAmount: Double = 0.00): AccountId =
            bank.registerAccount(DebitAccount(bank, this(), initialAmount))
    }

    override fun recalculateSavings(today: CustomDate) {
        accumulated += total * interestRate
        super.recalculateSavings(today)
    }

}