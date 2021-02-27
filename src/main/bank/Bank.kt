package bank

import account.Account
import bank.config.BankConfig
import bank.id.AccountId
import bank.id.ClientId
import client.Client
import date.CustomDate

class Bank private constructor(val bankConfig: BankConfig) {

    private val clients = mutableListOf<Client>()
    private val accounts = mutableListOf<Account>()

    companion object PaySystem {
        private val banks = mutableMapOf<String, Bank>()

        operator fun invoke(bankConfig: BankConfig): Bank {
            require(bankConfig.name !in banks) { "Bank with name '${bankConfig.name}' already exists" }
            return Bank(bankConfig).also { banks[bankConfig.name] = it }
        }

        operator fun get(name: String): Bank {
            require(name in banks) { "No bank with name '$name'" }
            return banks[name]!!
        }

        fun updateAll(today: CustomDate) {
            banks.values.forEach { bank ->
                bank.accounts.forEach { account ->
                    account.recalculateSavings(today)
                }
            }
        }
    }

    fun registerClient(client: Client): ClientId {
        clients.add(client)
        return ClientId(bankConfig.name, clients.size - 1)
    }

    infix fun clientNo(id: Int): Client {
        require(id < clients.size) { "Invalid client id" }
        return clients[id]
    }

    fun registerAccount(account: Account): AccountId {
        accounts.add(account)
        return AccountId(bankConfig.name, accounts.size - 1)
    }

    infix fun accountNo(id: Int): Account {
        require(id < accounts.size) { "Invalid account id" }
        return accounts[id]
    }

}