## 1. Padrão de Mensagens dos Commits

## Guia de Commit Semântico com Emojis

| Tipo | Emoji | Descrição | Exemplo de Commit |
|:---:|:---:|:---|:---|
| **feat** | ✨ | Nova funcionalidade, recurso ou feature. | `feat (auth): Criação da rota de login por token` |
| **fix** | 🐛 | Correção de um bug. | `fix (#45): Correção do componente de seleção de município` |
| **docs** | 📚 | Adição ou alteração em documentação (README, guias, manuais). | `docs: 📚 Inclusão do diagrama de modelo de BD` |
| **style** | 💅 | Mudanças de formatação de código (espaçamento, ponto-e-vírgula, etc.) que não alteram a lógica. | `style: Ajuste de nomes de variáveis para camelCase` |
| **refactor**| 🔨 | Refatoração de código que não altera a funcionalidade externa. | `refactor: Extração da lógica de cálculo de média para serviço` |
| **test** | ✅ | Adição, correção ou modificação de testes. | `test: Adição de testes de unidade para validação de dados` |
| **chore** | ⚙️ | Atualizações de rotina que não impactam o código de produção (ex: atualização de dependências, configs). | `chore: Atualização das dependências do Node.js` |
**<id_demandaN>** – Identificador da demanda criada na ferramenta de gestão de Stories/Tasks que o Time estiver usando (Github Issues, Jira Software, GitLab Issues etc.), podendo estar entre 1 e N.

**<descrição da entrega feita no commit>** – Descrição clara sobre o que está sendo entregue no commit criado e enviado para o Git.

---

## 2. Estratégia de Branch

### • GitHub Flow

**Funcionamento:**
- A branch `main` (ou `master`) é sempre a versão estável.
- A beta-main funciona para a prévia da main original/oficial.
- Cada funcionalidade ou correção é feita em uma **branch separada**.
- Após o desenvolvimento, abre-se um **Pull Request** para revisão e merge.
