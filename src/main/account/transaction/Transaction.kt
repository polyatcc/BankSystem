package account.transaction

import account.transaction.action.Action

class Transaction(vararg actionArray: ActionAtom) : Action() {

    private val actions = actionArray.toList()

    private fun revokeAll(actionsToRevoke: Iterable<ActionAtom>): MutableList<Pair<ActionAtom, IllegalArgumentException>> {
        val failedRevokes = mutableListOf<Pair<ActionAtom, IllegalArgumentException>>()
        for (action in actionsToRevoke) {
            try {
                action.revoke()
            } catch (e: IllegalArgumentException) {
                failedRevokes.add(action to e)
            }
        }
        return failedRevokes
    }

    override fun call() {
        super.call()
        for (i in actions.indices) {
            try {
                actions[i].call()
            } catch (e: IllegalArgumentException) {
                fail()
                val failedActions = revokeAll(actions.subList(0, i).asReversed())
                failedActions.add(actions[i] to e)
                throw TransactionException(status, failedActions)
            }
        }
        commit()
    }

    override fun revoke() {
        super.revoke()
        val failedRevokes = revokeAll(actions.asReversed())
        if (failedRevokes.isNotEmpty()) {
            fail()
            throw TransactionException(status, failedRevokes)
        }
        commit()
    }

}