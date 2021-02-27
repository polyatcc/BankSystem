package account.transaction

import account.Account
import account.transaction.action.Action
import bank.id.AccountId
import date.CustomDate

sealed class ActionAtom(
    protected val accountId: AccountId,
    protected val amount: Double
) : Action() {

    protected fun perform(action: Account.() -> Unit) {
        try {
            accountId().action()
        } catch (e: IllegalArgumentException) {
            fail()
            throw e
        }
        commit()
    }

}

class Raise(accountId: AccountId, amount: Double) : ActionAtom(accountId, amount) {

    override fun call() {
        super.call()
        perform {
            raise(amount)
        }
    }

    override fun revoke() {
        super.revoke()
        perform {
            forceWithdraw(amount)
        }
    }

    override fun toString(): String = "Raise $amount to $accountId"

}

class Withdraw(accountId: AccountId, amount: Double, val today: CustomDate) : ActionAtom(accountId, amount) {

    override fun call() {
        super.call()
        perform {
            withdraw(amount, today)
        }
    }

    override fun revoke() {
        super.revoke()
        perform {
            raise(amount)
        }
    }

    override fun toString(): String = "Withdraw $amount from $accountId on $today"

}