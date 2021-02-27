package account

import bank.Bank
import bank.id.AccountId
import bank.id.ClientId
import client.Client
import date.CustomDate

class DepositAccount private constructor(
    bank: Bank,
    owner: Client,
    initialAmount: Double,
    private val dueDate: CustomDate
) : Account(bank, owner, initialAmount) {

    private val interestRate = bank.bankConfig.accountConfig.depositDailyInterest(total)

    companion object Factory {
        class DepositAccountParams(val initialAmount: Double, val dueDate: CustomDate) {
            init {
                require(initialAmount >= 0) { "Can't create a deposit with negative amount" }
                require(dueDate > CustomDate.nowProvider()) { "Can't create a deposit for past date" }
            }
        }

        infix fun Double.depositUntil(dueDate: CustomDate) =
            DepositAccountParams(this, dueDate)

        infix fun ClientId.registerDepositAccount(params: DepositAccountParams): AccountId =
            bank.registerAccount(DepositAccount(bank, this(), params.initialAmount, params.dueDate))
    }

    override fun checkWithdraw(amount: Double, today: CustomDate) {
        super.checkWithdraw(amount, today)
        require(today > dueDate) { "Can't withdraw from a deposit before it's due date" }
    }

    override fun recalculateSavings(today: CustomDate) {
//        val today = LocalDate.now()
        if (today <= dueDate) {
            accumulated += total * interestRate
        }
        if (today.isLastInMonth() || today == dueDate) {
            total += accumulated
            accumulated = 0.00
        }
    }

}