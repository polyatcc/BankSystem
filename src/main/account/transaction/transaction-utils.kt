package account.transaction

import bank.id.AccountId
import date.CustomDate

fun transfer(
    sourceAccountId: AccountId,
    targetAccountId: AccountId,
    amount: Double,
    today: CustomDate = CustomDate.nowProvider()
): Transaction {
    return Transaction(
        Withdraw(sourceAccountId, amount, today),
        Raise(targetAccountId, amount)
    )
}