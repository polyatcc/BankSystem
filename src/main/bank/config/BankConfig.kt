package bank.config

import account.config.AccountConfig

class BankConfig(
    val name: String,
    val untrustworthyLimit: Double,
    val accountConfig: AccountConfig
)