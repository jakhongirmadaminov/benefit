package uz.magnumactive.benefit.util

object TransTypeTranslator {


    fun translate(transType: String?): CharSequence? {
        return transType?.let {
            when (transType) {
                TransactionTypes.PAYMENT_AT_TERMINAL.code.toString() -> {
                    when (AppPrefs.language) {
                        "uz" -> "Оплата в обычном терминале"
                        "en" -> "Оплата в обычном терминале"
                        else -> "Оплата в обычном терминале"
                    }
                }
                TransactionTypes.WITHDRAW_AT_ATM.code.toString() -> {
                    when (AppPrefs.language) {
                        "uz" -> "Снятие наличных в банкомате"
                        "en" -> "Снятие наличных в банкомате"
                        else -> "Снятие наличных в банкомате"
                    }
                }
                TransactionTypes.CREDIT_EPOS.code.toString() -> {
                    when (AppPrefs.language) {
                        "uz" -> "Кредит EPOS"
                        "en" -> "Кредит EPOS"
                        else -> "Кредит EPOS"
                    }
                }
                TransactionTypes.DEBIT_EPOS.code.toString() -> {
                    when (AppPrefs.language) {
                        "uz" -> "Дебит EPOS"
                        "en" -> "Дебит EPOS"
                        else -> "Дебит EPOS"
                    }
                }
                TransactionTypes.WITHDRAW_AT_TERMINAL.code.toString() -> {
                    when (AppPrefs.language) {
                        "uz" -> "Снятие наличных с терминала"
                        "en" -> "Снятие наличных с терминала"
                        else -> "Снятие наличных с терминала"
                    }
                }
                else -> it
            }
        }

    }


}
