package bank.id

import bank.Bank
import client.Client

data class ClientId(val bankName: String, val id: Int) {

    val bank: Bank
        get() = Bank.PaySystem[bankName]

    operator fun invoke(): Client = bank clientNo id

    override fun toString(): String = "${bankName}#client_${id}"

}
