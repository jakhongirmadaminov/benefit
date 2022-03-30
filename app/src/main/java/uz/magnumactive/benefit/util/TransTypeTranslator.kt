package uz.magnumactive.benefit.util

object TransTypeTranslator {


    fun translate(transType: String?): CharSequence? {
        return transType?.let {
            when (transType) {
                "774" -> {
                    when (AppPrefs.language) {
                        "uz" -> "Оплата в обычном терминале"
                        "en" -> "Оплата в обычном терминале"
                        else -> "Оплата в обычном терминале"
                    }
                }
                "700" -> {
                    when (AppPrefs.language) {
                        "uz" -> "Снятие наличных в банкомате"
                        "en" -> "Снятие наличных в банкомате"
                        else -> "Снятие наличных в банкомате"
                    }
                }
                "760" -> {
                    when (AppPrefs.language) {
                        "uz" -> "Кредит EPOS"
                        "en" -> "Кредит EPOS"
                        else -> "Кредит EPOS"
                    }
                }
                "683" -> {
                    when (AppPrefs.language) {
                        "uz" -> "Дебит EPOS"
                        "en" -> "Дебит EPOS"
                        else -> "Дебит EPOS"
                    }
                }
                "777" -> {
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
