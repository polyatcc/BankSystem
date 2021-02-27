@file:Suppress("NON_EXHAUSTIVE_WHEN")

package account.transaction.action

abstract class Action {

    protected var status = ActionStatus.None
        private set

    open fun call() {
        require(status == ActionStatus.None) { "Actions can not be reused" }
        status = ActionStatus.Called
    }

    open fun revoke() {
        require(status == ActionStatus.Commited) { "Only commited actions can be revoked" }
        status = ActionStatus.RevokeCalled
    }

    protected fun fail() {
        when (status) {
            ActionStatus.Called -> status = ActionStatus.Failed
            ActionStatus.RevokeCalled -> status = ActionStatus.RevokeFailed
        }
    }

    protected fun commit() {
        when (status) {
            ActionStatus.Called -> status = ActionStatus.Commited
            ActionStatus.RevokeCalled -> status = ActionStatus.RevokeCommited
        }
    }

}