# agendador-horarios

# 📅 Agenda Dourada - API (Back-end) Em andamento

API RESTful desenvolvida com **Java** e **Spring Boot** para o gerenciamento e agendamento de serviços da plataforma **Agenda Dourada**. O sistema controla a disponibilidade de horários, profissionais e o registro completo dos agendamentos de clientes.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
    - Spring Web (Criação da API REST)
    - Spring Data JPA (Persistência de dados)
    - Spring Validation / Bean Validation
- **Banco de Dados:** H2 Database (desenvolvimento/testes) / PostgreSQL ou MySQL
- **Maven:** Gerenciador de dependências e build

---

## 📌 Modelo de Dados (DTO)

O payload esperado pela aplicação segue o formato `AgendamentoRequestDTO`:

```json
{
  "servico": "Corte e Escova",
  "produto": "Hidratação com Óleo de Abacate",
  "profissional": "Nicole",
  "dataHoraAgendamento": "2026-08-15T14:00:00",
  "cliente": "Maria Silva",
  "telefoneCliente": "21999998888"
}

