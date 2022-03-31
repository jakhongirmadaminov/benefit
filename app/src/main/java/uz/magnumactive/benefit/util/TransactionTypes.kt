package uz.magnumactive.benefit.util

enum class TransactionTypes(val code: Int) {
    PAYMENT_AT_TERMINAL(774),
    CREDIT_EPOS(760),
    DEBIT_EPOS(683),
    WITHDRAW_AT_ATM(700),
    WITHDRAW_AT_TERMINAL(777),

}