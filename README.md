[README.md](https://github.com/user-attachments/files/35084819/README.md)
# 🫧 BubbleTech — Mobile

Aplicativo Android de **notícias de tecnologia**, feito em **Kotlin + Jetpack Compose**. O usuário entra com e-mail/senha ou conta Google, navega pelas notícias por categoria, curte e comenta.

O app consome a API do projeto **TechBubble_BackEnd** (Node.js + TypeScript), e a autenticação é feita pelo **Firebase Authentication**.

---

## ✨ Funcionalidades

- **Splash screen** animada com o logo do app
- **Login e cadastro** com e-mail e senha (Firebase Auth)
- **Login com Google** (Credential Manager)
- **Recuperação de senha** por e-mail
- **Feed de notícias** com:
  - filtro por categoria (Hot, IA, Games, Desenvolvimento, Mercado)
  - busca
  - paginação (carrega mais ao rolar)
- **Detalhe da notícia** com:
  - imagem, conteúdo, fonte e data
  - curtir / descurtir
  - comentar, editar e excluir os próprios comentários

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navegação | Navigation Compose |
| Arquitetura | MVVM (ViewModel + Repository) |
| Rede | Retrofit + Gson + OkHttp |
| Imagens | Coil |
| Autenticação | Firebase Auth + Google Sign-In (Credential Manager) |
| Assíncrono | Kotlin Coroutines |

- **minSdk:** 24 (Android 7.0)
- **targetSdk / compileSdk:** 37

---

## 📁 Estrutura do projeto

```
app/src/main/java/ffc/app/bubbletech/
├── auth/          # Firebase Auth, cliente HTTP (Retrofit) e endpoints da API
├── components/    # Componentes reutilizáveis (cards, chips, barra de busca, bottom bar)
├── data/          # Repositórios de dados
├── model/         # Modelos (NewsArticle, Comment, etc.)
├── navigation/    # Rotas do app e splash screen
├── screens/       # Telas (Login, Cadastro, Home, Detalhe) e seus ViewModels
├── ui/theme/      # Cores, tipografia e tema
└── MainActivity.kt
```

---

## 🔌 Endpoints consumidos

Todas as requisições enviam o token do Firebase no header `Authorization: Bearer <token>`.

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/me` | Dados do usuário logado |
| GET | `/news?category=&search=&limit=&cursor=` | Lista de notícias (paginada) |
| GET | `/news/{id}` | Detalhe de uma notícia |
| POST | `/news/{id}/like` | Curtir notícia |
| DELETE | `/news/{id}/like` | Descurtir notícia |
| GET | `/news/{id}/comments` | Listar comentários |
| POST | `/news/{id}/comments` | Criar comentário |
| PATCH | `/news/{id}/comments/{commentId}` | Editar comentário |
| DELETE | `/news/{id}/comments/{commentId}` | Excluir comentário |

---

## 🚀 Como rodar

### Pré-requisitos

- [Android Studio](https://developer.android.com/studio) (versão recente)
- [Node.js](https://nodejs.org/) (para o backend)
- Um emulador Android ou celular com depuração USB ativada
- O arquivo **`google-services.json`** do projeto Firebase (veja abaixo)

### 1. Clonar o repositório

```bash
git clone https://github.com/JoaoMarcosRF/bubbleteech_mobile.git
```

### 2. Adicionar o `google-services.json`

Esse arquivo **não fica no repositório** (está no `.gitignore`). Peça para alguém da equipe ou baixe no [Firebase Console](https://console.firebase.google.com) → Configurações do projeto → app Android `ffc.app.bubbletech`.

Coloque o arquivo em:

```
app/google-services.json
```

> ⚠️ Se copiar o conteúdo pelo navegador, desative o tradutor automático. Ele traduz as chaves do JSON (ex.: `"client"` vira `"cliente"`) e o build quebra.

### 3. Subir o backend

Na pasta do **TechBubble_BackEnd**:

```bash
npm install
npm run start:dev
```

Por padrão, a API roda na porta **3333**. Deixe o terminal aberto.

### 4. Ligar o emulador e redirecionar a porta

O app acessa a API em `http://127.0.0.1:8080/`. Dentro do emulador, esse endereço aponta para o próprio emulador, então é preciso redirecionar para o backend no PC. **Com o emulador já ligado**, rode:

```powershell
# Windows (PowerShell)
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" reverse tcp:8080 tcp:3333
```

```bash
# Linux / macOS (com o adb no PATH)
adb reverse tcp:8080 tcp:3333
```

> Esse redirecionamento se perde sempre que o emulador é reiniciado. Rode de novo quando isso acontecer.

### 5. Rodar o app

No Android Studio, selecione o módulo **`app`**, escolha o emulador/dispositivo e clique em **Run ▶️**.

---

## 🧯 Problemas comuns

| Erro | Causa | Solução |
| --- | --- | --- |
| `File google-services.json is missing` | Falta o arquivo do Firebase | Veja o passo 2 |
| `unexpected end of stream on http://127.0.0.1:8080/` / "sem conexão com o servidor" | Backend desligado ou porta não redirecionada | Veja os passos 3 e 4 |
| Login com Google não funciona | SHA-1 da máquina não cadastrado no Firebase | Adicione o SHA-1 do seu debug keystore no Firebase Console |

---

## 👥 Equipe

Projeto desenvolvido para a disciplina do curso de **Análise e Desenvolvimento de Sistemas (ADS)**.
