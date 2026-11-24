package ffc.app.bubbletech.model

enum class NewsCategory(val label: String, val apiValue: String) {
    IA("IA", "ia"),
    CYBERSECURITY("Cibersegurança", "ciberseguranca"),
    GAMES("Games", "games"),
    STARTUPS("Startups", "startups"),
    DEVELOPMENT("Desenvolvimento", "desenvolvimento"),
    MARKET("Mercado", "mercado");

    companion object {
        val labels: List<String> = entries.map { it.label }

        fun apiValueOf(label: String): String? = entries.find { it.label == label }?.apiValue
    }
}
