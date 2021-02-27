package account.transaction.action

enum class ActionStatus {
    None,
    Called,
    Failed,
    Commited,
    RevokeCalled,
    RevokeFailed,
    RevokeCommited
}