package ffc.app.bubbletech.data

import ffc.app.bubbletech.model.News
import ffc.app.bubbletech.model.Post

class FakeRepository {

    fun getPosts(): List<Post>{
        return listOf(
            Post(
                id = 1,
                title = "Anthropic lança versão do Opus 6.0",
                category = "IA",
                news = listOf(
                    News(
                        headline = "Nova versão do Opus chega no mercado",
                        summary = "Modelo promete raciocínio mais longo e menos erros em código.",
                        source = "Tecmundo",
                        publishedAt = "2026-10-01",
                        referenceLink = "https://tecmundo.com.br/opus-6"
                    ),
                    News(
                        headline = "Opus 6.0: o que muda para desenvolvedores",
                        summary = "Janela de contexto maior e preço menor por token.",
                        source = "The Verge",
                        publishedAt = "2026-10-01",
                        referenceLink = "https://theverge.com/opus-6"
                    )
                ),
                likeCount = 342,
                commentCount = 57
            ),
            Post(
                id = 2,
                title = "GTA VI ganha novo trailer",
                category = "Games",
                news = listOf(
                    News(
                        headline = "Rockstar divulga terceiro trailer de GTA VI",
                        summary = "Vídeo mostra novas áreas de Vice City e confirma data de lançamento.",
                        source = "IGN Brasil",
                        publishedAt = "2026-09-30",
                        referenceLink = "https://br.ign.com/gta-6-trailer-3"
                    ),
                    News(
                        headline = "Trailer de GTA VI bate recorde de visualizações",
                        summary = "Foram mais de 100 milhões de views nas primeiras 24 horas.",
                        source = "Olhar Digital",
                        publishedAt = "2026-10-01",
                        referenceLink = "https://olhardigital.com.br/gta-6-recorde"
                    )
                ),
                likeCount = 1280,
                commentCount = 213
            ),
            Post(
                id = 3,
                title = "Kotlin 2.4 é lançado",
                category = "Desenvolvimento",
                news = listOf(
                    News(
                        headline = "JetBrains anuncia Kotlin 2.4",
                        summary = "Compilador mais rápido e melhorias no Compose Multiplatform.",
                        source = "InfoQ",
                        publishedAt = "2026-09-28",
                        referenceLink = "https://infoq.com/kotlin-2-4"
                    ),
                    News(
                        headline = "Kotlin 2.4 traz novidades para Android",
                        summary = "Google recomenda atualização para novos projetos.",
                        source = "Android Developers Blog",
                        publishedAt = "2026-09-29",
                        referenceLink = "https://android-developers.googleblog.com/kotlin-2-4"
                    )
                ),
                likeCount = 98,
                commentCount = 12
            ),
            Post(
                id = 4,
                title = "Startup brasileira de dados recebe aporte milionário",
                category = "Mercado",
                news = listOf(
                    News(
                        headline = "Startup de dados capta R$ 50 milhões",
                        summary = "Rodada série B será usada para expansão na América Latina.",
                        source = "Exame",
                        publishedAt = "2026-09-27",
                        referenceLink = "https://exame.com/startup-dados-aporte"
                    ),
                    News(
                        headline = "Investimento em startups de dados cresce no Brasil",
                        summary = "Setor teve alta de 30% em aportes no último trimestre.",
                        source = "StartSe",
                        publishedAt = "2026-09-28",
                        referenceLink = "https://startse.com/dados-investimento"
                    )
                ),
                likeCount = 45,
                commentCount = 8
            )
        )
    }

}
