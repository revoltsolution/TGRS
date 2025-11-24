# API 2º Semestre - Banco de dados (Noturno).

<p align="center">
      <img src="settings/REVOLT_SOLUTIONS.png" alt="logo da Revolt Solutions" width="550">
</p>
<p align="center">
      <img src="settings/TGRS.PNG" alt="logo da Revolt Solutions" width="600">
      <h2 align="center"> TGRS - Gerenciador de Atividades Academicas</h2>
</p>

## 🔎 O que é?

A API tem como objetivo facilitar o processo de confecção, envio e correção dos Trabalhos de Graduação na modalidade Portfólio. Isso ocorre pelo fato dos alunos enfrentarem dificuldades com atrasos no retorno dos orientadores e com a geração do arquivo final em formato Markdown, enquanto os professores sofrem para organizar históricos de correções e acompanhar versões anteriores das seções.
A plataforma centraliza a comunicação e o acompanhamento dos TGs em uma plataforma única, garantindo feedback mais ágil, histórico de interações e organização das versões. Além disso, permite que os professores da disciplina acompanhem o progresso dos alunos e os portfólios já finalizados, eliminando a dependência de e-mails, Teams e documentos manuais.

> Status do Projeto: Concluído  ✅
>  
> Manual de Usuário: [Manual TGRS](Documentos/Manual de Usuario.pdf). 📄
> 
> Pasta de Documentação: [Documentos](Documentos). 📚

## 📋 Backlog do Produto

| US | Prioridade | Função | Story Points | Sprint | Requisito do Cliente | Status |
| :---: | :--------: | :--------------------------------------------- | :-----------: | :----------: | :----: | :----: | 
| **US01** | Alta | Como administrador, quero importar um arquivo CSV com as relações entre alunos e professores orientadores para agilizar o cadastro e evitar retrabalho manual. | 34 | 2 | RF01 RF02 RF03 | Em Andamento |
| **US03** | Alta | Como professor-orientador do TG, quero associar um aluno a um professor orientador para formalizar a orientação do portfólio, reduzindo atrasos e evitando gastos extras. | 21 | 2 | RF03 RF06 | Em andamento |
| **US04** | Alta | Como aluno, quero enviar uma seção do meu TG para o orientador para que ele possa revisar e dar feedback, economizando tempo e reduzindo custos com correções. | 34 | 2 | RF04 RF06 | Não iniciado |
| **US05** | Alta | Como orientador, quero corrigir e registrar feedback em cada seção enviada pelo aluno para acompanhar sua evolução. | 21 | 3 | RF05 RF06 | Em execução |
| **US02** | Média | Como administrador, quero atualizar automaticamente as relações entre alunos e professores ao importar um novo CSV, garantindo que o sistema reflita sempre a situação mais recente da turma. | 13 | 3 | RF03 RF06 | Não iniciado |
| **US06** | Média | Como professor-orientador, quero visualizar o histórico de revisões de cada seção para acompanhar a evolução do trabalho, evitando desperdício de tempo e garantindo melhor eficácia. | 5 | 1 | RF07 RF08| Concluido |
| **US07** | Baixa | Como professor de disciplina, quero visualizar o status de conclusão dos portfólios para identificar quais alunos já finalizaram todas as seções, reduzindo atrasos que podem gerar custos adicionais. | 13 | 3 | RF09 RF10 | Não iniciado |
| **US08** | Baixa | Como aluno, quero gerar automaticamente o TG final em formato Markdown (MD) para entregar de acordo com o padrão exigido pela instituição, economizando tempo e custos com formatação manual. | 21 | 3 | RF11 RF12 | Não iniciado |

## ⚙️ DoR - Definition of Ready <a id="dor"></a>

* User Stories com **Critérios de Aceitação**
* Subtarefas divididas **a partir das US**
* Design no **Canva e Scene Builder**
* Modelagem do **Banco de Dados**
* Banco de Dados **Vetorizado** do Cliente

## ⚙️ DoD - Definition of Done <a id="dod"></a>

* Manual de Usuário
* Documentação da API
* Código completo

## 📅 Cronograma
  | Evento | Data |
  | --- | --- |
  | Kick-off geral | 25/08 a 29/08 |
  | [Sprint-1](Sprints/Sprint1) | 08/09 a 28/09 |
  | Sprint Review/Planning | 29/09 a 03/10 |
  |  [Sprint-2](Sprints/Sprint2) | 06/10 a 26/10 |
  | Sprint Review/Planning | 27/10 a 31/10 |
  |  [Sprint-3](Sprints/Sprint3) | 03/11 a 23/11 |
  | Sprint Review/Planning | 24/11 a 28/11 |
  | Feira de Soluções | 04/12 |

## 🛠 Tecnologias Utilizadas
<h4 align="center">
 <a href="https://www.atlassian.com/software/jira">
   <img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"/>
 </a>
 <a href="https://www.java.com/">
   <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
 </a>
 <a href="https://www.jetbrains.com/idea/">
   <img src="https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"/>
 </a>
 <a href="https://gluonhq.com/products/scene-builder/">
   <img src="https://img.shields.io/badge/Scene%20Builder-2C2255?style=for-the-badge&logo=java&logoColor=white"/>
 </a>
 <a href="https://github.com/">
   <img src="https://img.shields.io/badge/GitHub-121011?style=for-the-badge&logo=github&logoColor=white"/>
 </a>
</h4>

## 📊 Modelo Entidade de Relacionamento
<img src="Documentos/DER.png" alt="logo da Revolt Solutions" width="1000">

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
