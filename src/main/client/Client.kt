package client

import bank.Bank
import bank.id.ClientId

class Client private constructor(
    var firstName: String, var lastName: String,
    var address: String? = null, var passport: String? = null
) {

    companion object Factory {
        fun Bank.registerClient(
            firstName: String, secondName: String,
            address: String? = null, passport: String? = null
        ): ClientId {
            return registerClient(
                Client(
                    firstName, secondName,
                    address, passport
                )
            )
        }
    }

    val confirmed
        get() = address != null && passport != null

}