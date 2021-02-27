import account.DebitAccount.Factory.registerDebitAccount
import account.DepositAccount.Factory.depositUntil
import account.DepositAccount.Factory.registerDepositAccount
import account.config.AccountConfig
import account.transaction.transfer
import bank.Bank
import bank.config.BankConfig
import client.Client.Factory.registerClient
import date.CustomDate
import date.Tick

fun main() {

    CustomDate.nowProvider = { Tick.now() }

    val sberbank = Bank(
        BankConfig(
            "Sberbank",
            10_000.00,
            AccountConfig(
                3.65,
                4.00,
                -100_000.00,
                mapOf(
                    50_000.00 to 3.00,
                    100_000.00 to 3.50
                ),
                4.00
            )
        )
    )

    val dmitry = sberbank.registerClient(
        "Dmitry",
        "Ivanov",
        "<address>",
        "0000 000000"
    )
    val polina = sberbank.registerClient(
        "Polina",
        "Tceneva",
        "<address>"
    )

    val account1 = dmitry.registerDebitAccount(
        25_000.00
    )
    val account2 = polina.registerDepositAccount(
        75_000.00 depositUntil Tick(31)
    )

    account1().raise(25_000.00)
    try {
        val t = transfer(account1, account2, 20_000.00)
        t.call()
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }

    val t = transfer(account1, account2, 9_000.00)
    t.call()
    t.revoke()

    println(account1().total)
    println(account2().total)

    val account3 = dmitry.registerDepositAccount(
        1000_000_000.00 depositUntil Tick(31)
    )

    for (i in 1..31) {
        account3().recalculateSavings(Tick(i))
        if (i > 28) {
            println(String.format("Day $i: %.9f", account3().total))
        }
    }

}