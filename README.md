# 🚀 Notification Service

Microserviço robusto e resiliente para envio de notificações, construído com **Spring Boot 3** e **Java 21**.
O sistema utiliza **mensageria assíncrona**, **cache distribuído** e **estratégias avançadas de retry** para garantir alta disponibilidade e tolerância a falhas.

---

## 🛠 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3**
* **RabbitMQ** – Mensageria assíncrona com suporte a DLQ
* **Redis** – Cache de templates de alta performance
* **JavaMailSender (Spring Mail)**
* **Mailtrap** – Simulador SMTP para testes
* **Docker & Docker Compose** – Orquestração da infraestrutura

---

# 🏗 Arquitetura do Sistema

O fluxo da informação segue o padrão abaixo:

```
Client → API → RabbitMQ → Consumer → Redis → SMTP Server
```

### 📌 Fluxo Detalhado

1. **API Endpoint**

   * Recebe requisição `POST` com os dados da notificação.

2. **RabbitMQ (Producer)**

   * A mensagem é publicada na fila `notification.queue`.

3. **Consumer**

   * Consome a mensagem de forma assíncrona.

4. **Redis**

   * Busca o template correspondente (`template:{nome}`).
   * Monta o corpo do e-mail substituindo variáveis dinâmicas.

5. **JavaMailSender**

   * Dispara o e-mail para o servidor SMTP configurado.

---

# 🛡 Resiliência e Tolerância a Falhas

Este projeto implementa padrões avançados de confiabilidade:

## 🔁 Retry Pattern com Exponential Backoff

* Em caso de falha no envio (ex: SMTP indisponível)
* O sistema realiza **até 3 tentativas automáticas**
* Intervalos crescentes entre tentativas (backoff exponencial)
* Evita sobrecarga no serviço externo

## ☠ Dead Letter Queue (DLQ)

* Após esgotar as tentativas de retry
* A mensagem é enviada para:

```
notification.queue.dlq
```

* Nenhuma mensagem é perdida
* Permite análise posterior de falhas

## 👀 DLQ Consumer

* Um consumidor dedicado monitora a fila de erro
* Gera logs e alertas para acompanhamento operacional

---

# 🚀 Como Executar o Projeto

## 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/notification-service.git
cd notification-service
```

---

## 2️⃣ Subir a Infraestrutura (Docker)

Certifique-se de ter o Docker instalado.

```bash
docker-compose up -d
```

Isso irá subir:

* RabbitMQ → [http://localhost:15672](http://localhost:15672)
  Usuário padrão: `guest`
  Senha padrão: `guest`

* Redis → `localhost:6379`

---

## 3️⃣ Configurar Variáveis de Ambiente

Configure as variáveis abaixo na sua IDE ou ambiente:

```
MAIL_USERNAME=seu_usuario_mailtrap
MAIL_PASSWORD=sua_senha_mailtrap
```

---

## 4️⃣ Popular o Redis com Templates

Conecte-se ao Redis:

```bash
docker exec -it redis redis-cli
```

Adicione um template de teste:

```bash
SET template:welcome-email "Olá {nome}, seu sistema está funcionando!"
```

---

# 📮 Endpoint Disponível

## POST `/notifications`

### 📥 Request Body

```json
{
  "destination": "junior@dev.com",
  "userName": "Junior",
  "template": "welcome-email",
  "channel": "EMAIL"
}
```

---

## 📤 Funcionamento Esperado

1. A API recebe a requisição
2. Publica na fila RabbitMQ
3. Consumer processa
4. Busca template no Redis
5. Envia e-mail via Mailtrap
6. Em caso de erro → Retry
7. Se falhar definitivamente → DLQ

---

# 📊 Estrutura de Filas

| Fila                     | Descrição                       |
| ------------------------ | ------------------------------- |
| `notification.queue`     | Fila principal de processamento |
| `notification.queue.dlq` | Fila de mensagens com falha     |

---

# 🔍 Observabilidade (Recomendado)

Para ambientes produtivos, recomenda-se integrar:

* Spring Actuator
* Prometheus
* Grafana
* Logs estruturados (JSON)

---

# 🎯 Diferenciais Técnicos

✔ Arquitetura orientada a eventos
✔ Comunicação assíncrona desacoplada
✔ Retry com backoff exponencial
✔ Dead Letter Queue
✔ Cache distribuído com Redis
✔ Infraestrutura containerizada
✔ Preparado para escalar horizontalmente

---

# 📌 Possíveis Evoluções

* Suporte a múltiplos canais (SMS, Push)
* Persistência de histórico de notificações
* Painel administrativo
* Métricas de entrega
* Rate Limiting
* Circuit Breaker (Resilience4j)

---

# 👨‍💻 Autor

**Arthenyo Carlos**
Especialista em TI | Java & Spring Boot Developer
