## 2. Padrão de Mensagens dos Commits

| **Tipo**     | **Descrição** | **Exemplo** |
|---------------|---------------|--------------|
| **feat** | Quando da adição de um recurso, uma “feature” (funcionalidade) | `feat (AB-1243, AB-56): Implementação dos repositórios usados nas operações com as tabelas de variações climáticas` |
| **fix** | Correção de um bug | `fix (#45): Correção do componente de seleção de município` |
| **docs** | Atualização de documentação | `docs (#45): inclusão de diagrama de modelo de BD para a aplicação` |
| **style** | Mudança de formatação, sem afetar o código | `style (AB-1243, AB-56): ajuste de nomes de variáveis para o padrão camelCase` |
| **refactor** | Refatoração do código, sem alterar funcionalidade | Seguir exemplos anteriores, alterando o tipo, IDs e descrições correspondentes |
| **test** | Adiciona ou modifica testes | Seguir exemplos anteriores, alterando o tipo, IDs e descrições correspondentes |
| **chore** | Atualizações menores que não impactam diretamente a funcionalidade do código | Seguir exemplos anteriores, alterando o tipo, IDs e descrições correspondentes |

**<id_demandaN>** – Identificador da demanda criada na ferramenta de gestão de Stories/Tasks que o Time estiver usando (Github Issues, Jira Software, GitLab Issues etc.), podendo estar entre 1 e N.

**<descrição da entrega feita no commit>** – Descrição clara sobre o que está sendo entregue no commit criado e enviado para o Git.

---

## 3. Estratégia de Branch

### • GitHub Flow

**Funcionamento:**
- A branch `main` (ou `master`) é sempre a versão estável.
- Cada funcionalidade ou correção é feita em uma **branch separada**.
- Após o desenvolvimento, abre-se um **Pull Request** para revisão e merge.
