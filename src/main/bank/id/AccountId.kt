package bank.id

import bank.Bank
import account.Account

data class AccountId(val bankName: String, val id: Int) {

    val bank: Bank
        get() = Bank.PaySystem[bankName]

    operator fun invoke(): Account = bank accountNo id

    override fun toString(): String = "${bankName}#account_${id}"

}