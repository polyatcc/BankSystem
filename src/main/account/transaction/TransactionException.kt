package account.transaction

import account.transaction.action.ActionStatus

class TransactionException(status: ActionStatus, actions: List<Pair<ActionAtom, IllegalArgumentException>>) :
    IllegalArgumentException(
        "Transaction aborted, status '$status':\n${
            actions.joinToString("\n") { " - ${it.first}: ${it.second}" }
        }"
    )