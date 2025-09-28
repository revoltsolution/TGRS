# API 2º Semestre - Banco de dados (Noturno).

<p align="center">
      <img src="settings/logo.PNG" alt="logo da Revolt Solutions" width="400">
      <h2 align="center"> Gerenciador de Atividades Academicas</h2>
</p>

## 🔎 O que é?

A API tem como objetivo facilitar o processo de confecção, envio e correção dos Trabalhos de Graduação na modalidade Portfólio. Isso ocorre pelo fato dos alunos enfrentarem dificuldades com atrasos no retorno dos orientadores e com a geração do arquivo final em formato Markdown, enquanto os professores sofrem para organizar históricos de correções e acompanhar versões anteriores das seções.
A plataforma centraliza a comunicação e o acompanhamento dos TGs em uma plataforma única, garantindo feedback mais ágil, histórico de interações e organização das versões. Além disso, permite que os professores da disciplina acompanhem o progresso dos alunos e os portfólios já finalizados, eliminando a dependência de e-mails, Teams e documentos manuais.

> Status do Projeto: Em andamento ⚙️ 

## 📋 Backlog do Produto <a id="backlog"></a>
|  US | Prioridade | Função                                                                                                   |             Responsável            |    Estado    | Sprint |
| :-: | :--------: | :------------------------------------------------------------------------------------------------------- | :--------------------------------: | :----------: | :----: |
|  1  |    Alta    | Modelagem (MER + User Stories)                                                                           |        Luan e Matheus Sousa        |   Concluído  |    1   |
|  2  |    Alta    | Infraestrutura / Ferramentas                                                                             |    Luis Eduardo e Matheus Sousa    |   Concluído  |    1   |
|  3  |    Alta    | Banco de Dados (Criação, Alimentação, Conexão, Validação)                                                | Luan, João Gabriel e Matheus Sousa | Não iniciado |    2   |
|  4  |    Alta    | Funcionalidade crítica – Aviso de conflito                                                               |            Luan e Samir            | Não iniciado |    3   |
|  5  |    Média   | Interfaces (Protótipo + Telas: inicial, cadastro, login, interface aluno, mentorados, histórico, logout) |   Luis Eduardo, Fernando e Samir   | Não iniciado |    2   |
|  6  |    Baixa   | Documentação do projeto                                                                                  |            Luis Eduardo            | Não iniciado |    3   |
|  7  |    Baixa   | Testes                                                                                                   |    Samir, Matheus e João Gabriel   | Não iniciado |    3   |
|  8  |    Baixa   | Entrega final                                                                                            |            Matheus Sousa           | Não iniciado |    3   |

## 📅 Cronograma
  | Evento | Data |
  | --- | --- |
  | Kick-off geral | 25/08 a 29/08 |
  | Sprint-1 | 08/09 a 28/09 |
  | Sprint Review/Planning | 29/09 a 03/10 |
  | Sprint-2 | 06/10 a 26/10 |
  | Sprint Review/Planning | 27/10 a 31/10 |
  | Sprint-3 | 03/11 a 23/11 |
  | Sprint Review/Planning | 24/11 a 28/11 |
  | Feira de Soluções | 04/12 |

## 🛠 Tecnologias Utilizadas
- **Java**
- **SceneBuilder**
- **Ide: Intellij**
- **MySQL**
- **Git/Github**
- **Br Modelo**

## 📌 User Stories do Cliente
| Título | User Story |
|--------|------------|
| Gerenciamento de Atividades | Como coordenador/administrador, quero cadastrar alunos no sistema para que seus Trabalhos de Graduação sejam acompanhados corretamente, evitando retrabalho manual e custos desnecessários. |
| Gerenciamento de Hierarquias | Como administrador, quero cadastrar professores orientadores para que seja possível associá-los aos alunos orientados. |
| Distribuição de Funções | Como coordenador de TG, quero associar um aluno a um professor orientador para formalizar a orientação do portfólio, reduzindo atrasos e evitando gastos extras. |
| Envio de Dados | Como aluno, quero enviar uma seção do meu TG para o orientador para que ele possa revisar e dar feedback, economizando tempo e reduzindo custos com correções. |
| Documentos do projeto | Como orientador, quero corrigir e registrar feedback em cada seção enviada pelo aluno para acompanhar sua evolução. |
| Visualização das Atribuições | Como orientador quero visualizar o histórico de revisões de cada seção para acompanhar a evolução do trabalho, evitando disperdicio de tempo e garantindo melhor eficácia. |
| Visualização de Status | Como professor da disciplina de TG, quero visualizar o status de conclusão dos portfólios para identificar quais alunos já finalizaram todas as seções, reduzindo atrasos que podem gerar custos adicionais. |
| Entrega de Dados | Como aluno, quero gerar automaticamente o TG final em formato Markdown (MD) para entregar de acordo com o padrão exigido pela instituição, economizando tempo e custos com formatação manual. |

## 📊 Modelo Entidade de Relacionamento
<img src="Documents/DER.png" alt="logo da Revolt Solutions" width="1000">

## 🎓 Equipe <a id="equipe"></a>

<div align="center">
  <table>
    <tr>
      <th>Membro</th>
      <th>Função</th>
      <th>Github</th>
      <th>Linkedin</th>
    </tr>
    <tr> 
      <td><p align="center">
      <img src="settings/Luis_Eduardo.png" width="150">
      <h2 align="center"> Luis Eduardo</h2></p></td>    
      <td>Scrum Master</td> 
      <td><a href="https://github.com/Lu1s3F">Perfil</a></td>       
      <td><a href="https://www.linkedin.com/in/luis-eduardo-mendes-28b51a355?utm_source=share_via&utm_content=profile&utm_medium=member_ios">Perfil</a></td>
    </tr>
    <tr>
      <td><p align="center">
      <img src="settings/Matheus_Souza.png" width="150">
      <h2 align="center"> Matheus de Souza</h2></p></td>
      <td>Product Owner</td>
      <td><a href="https://github.com/matheus23sjc">Perfil</a></td>
      <td><a href="https://www.linkedin.com/in/matheussouza23sjc/">Perfil</a></td>
    </tr>
    <tr>
      <td><p align="center">
      <img src="settings/Samir_Kassen.png" width="150">
      <h2 align="center"> Samir Kassen</h2></p></td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/Siqsamir">Perfil</a></td>
      <td><a href="https://www.linkedin.com/in/samir-siqueira-06012a363?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app">Perfil</a></td>
    </tr>
    <tr>
      <td><p align="center">
      <img src="settings/Luan_Santos.png" width="150">
      <h2 align="center"> Luan Santos</h2></p></td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/SantoszLuan">Perfil</a></td>
      <td><a href="https://www.linkedin.com/in/luansantoosz">Perfil</a></td>
    </tr>
    <tr>
      <td><p align="center">
      <img src="settings/Joao_Gabriel.png" width="150">
      <h2 align="center"> Joao Gabriel</h2></p></td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/jjoaoGabriel">Perfil</a></td>
      <td><a href="https://www.linkedin.com/in/joao-gabriel-fernandes-de-sousa-silva-292b9a2bb?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app">Perfil</a></td>
    </tr>
    <tr>
      <td><p align="center">
      <img src="settings/Fernando_Santos.png" width="150">
      <h2 align="center"> Fernando Santos</h2></p></td>    
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/fernandosantos09">Perfil</a></td>
      <td><a href="https://www.linkedin.com/in/santosfernando09">Perfil</a></td>
    </tr>
  </table>
</div>
