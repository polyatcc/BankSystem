package account

import bank.Bank
import client.Client
import date.CustomDate

abstract class Account(
    val bank: Bank,
    val owner: Client,
    initialAmount: Double
) {

    init {
        require(initialAmount >= 0) { "Can't create an account with negative amount" }
    }

    var total = initialAmount
        protected set
    protected var accumulated: Double = 0.0

    private val trustworthy: Boolean
        get() = owner.confirmed

    protected open fun checkRaise(amount: Double) {
        require(amount > 0) { "Can't raise by non-positive amount" }
        if (!trustworthy) {
            require(amount <= bank.bankConfig.untrustworthyLimit) { "Raise is limited" }
        }
    }

    protected open fun checkForceWithdraw(amount: Double) {
        require(amount > 0) { "Can't withdraw non-positive amount" }
        require(amount <= total) { "Can't withdraw more then available" }
    }

    protected open fun checkWithdraw(amount: Double, today: CustomDate = CustomDate.nowProvider()) {
        checkForceWithdraw(amount)
        if (!trustworthy) {
            require(amount <= bank.bankConfig.untrustworthyLimit) { "Withdraw is limited" }
        }
    }

    fun raise(amount: Double) {
        checkRaise(amount)
        total += amount
    }

    fun withdraw(amount: Double, today: CustomDate = CustomDate.nowProvider()) {
        checkWithdraw(amount, today)
        total -= amount
    }

    fun forceWithdraw(amount: Double) {
        checkForceWithdraw(amount)
        total -= amount
    }

    open fun recalculateSavings(today: CustomDate) {
//        val today = LocalDate.now()
        if (today.isLastInMonth()) {
            total += accumulated
            accumulated = 0.00
        }
    }

}