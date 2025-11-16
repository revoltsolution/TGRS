package org.api.trabalhodegraduacao.entities;

// 1. Importação de Date removida
import java.time.LocalDateTime; // 2. Importação correta para DATETIME

public class Secao {

    // 3. idSecao REMOVIDO (não existe no seu banco)

    // --- Chave Primária Composta ---
    private LocalDateTime data; // 4. Tipo CORRIGIDO
    private String emailAluno;
    private String emailOrientador;
    // --------------------------------

    private String identificacaoProjeto;
    private String empresaParceira;
    private String problema;
    private String solucao;
    private String linkRepositorio;
    private String tecnologiasUtilizadas;
    private String contribuicoesPessoais;
    private String descricaoSoft;
    private String descricaoHard;
    private String historicoProfissional;
    private String historicoAcademico;
    private String motivacao;
    private int ano;
    private char periodo;
    private char semestre;
    private int idTG;
    private boolean isIdentificacaoOk;
    private boolean isEmpresaOk;
    private boolean isProblemaOk;
    private boolean isSolucaoOk;
    private boolean isLinkOk;
    private boolean isTecnologiasOk;
    private boolean isContribuicoesOk;
    private boolean isSoftskillsOk;
    private boolean isHardskillsOk;
    private boolean isHistProfOk;
    private boolean isHistAcadOk;
    private boolean isMotivacaoOk;
    private boolean isAnoOk;
    private boolean isPeriodoOk;
    private boolean isSemestreOk;

    // Construtor vazio (usado pelo DAO)
    public Secao() {
    }

    public boolean isIdentificacaoOk() {
        return isIdentificacaoOk;
    }

    public void setIdentificacaoOk(boolean identificacaoOk) {
        isIdentificacaoOk = identificacaoOk;
    }

    public boolean isEmpresaOk() {
        return isEmpresaOk;
    }

    public void setEmpresaOk(boolean empresaOk) {
        isEmpresaOk = empresaOk;
    }

    public boolean isProblemaOk() {
        return isProblemaOk;
    }

    public void setProblemaOk(boolean problemaOk) {
        isProblemaOk = problemaOk;
    }

    public boolean isSolucaoOk() {
        return isSolucaoOk;
    }

    public void setSolucaoOk(boolean solucaoOk) {
        isSolucaoOk = solucaoOk;
    }

    public boolean isLinkOk() {
        return isLinkOk;
    }

    public void setLinkOk(boolean linkOk) {
        isLinkOk = linkOk;
    }

    public boolean isTecnologiasOk() {
        return isTecnologiasOk;
    }

    public void setTecnologiasOk(boolean tecnologiasOk) {
        isTecnologiasOk = tecnologiasOk;
    }

    public boolean isContribuicoesOk() {
        return isContribuicoesOk;
    }

    public void setContribuicoesOk(boolean contribuicoesOk) {
        isContribuicoesOk = contribuicoesOk;
    }

    public boolean isSoftskillsOk() {
        return isSoftskillsOk;
    }

    public void setSoftskillsOk(boolean softskillsOk) {
        isSoftskillsOk = softskillsOk;
    }

    public boolean isHardskillsOk() {
        return isHardskillsOk;
    }

    public void setHardskillsOk(boolean hardskillsOk) {
        isHardskillsOk = hardskillsOk;
    }

    public boolean isHistProfOk() {
        return isHistProfOk;
    }

    public void setHistProfOk(boolean histProfOk) {
        isHistProfOk = histProfOk;
    }

    public boolean isHistAcadOk() {
        return isHistAcadOk;
    }

    public void setHistAcadOk(boolean histAcadOk) {
        isHistAcadOk = histAcadOk;
    }

    public boolean isMotivacaoOk() {
        return isMotivacaoOk;
    }

    public void setMotivacaoOk(boolean motivacaoOk) {
        isMotivacaoOk = motivacaoOk;
    }

    public boolean isAnoOk() {
        return isAnoOk;
    }

    public void setAnoOk(boolean anoOk) {
        isAnoOk = anoOk;
    }

    public boolean isPeriodoOk() {
        return isPeriodoOk;
    }

    public void setPeriodoOk(boolean periodoOk) {
        isPeriodoOk = periodoOk;
    }

    public boolean isSemestreOk() {
        return isSemestreOk;
    }

    public void setSemestreOk(boolean semestreOk) {
        isSemestreOk = semestreOk;
    }

    // 5. Construtor longo CORRIGIDO (sem idSecao, com LocalDateTime)
    public Secao(String identificacaoProjeto, String empresaParceira, String problema, String solucao, String linkRepositorio, String tecnologiasUtilizadas, String contribuicoesPessoais, String descricaoSoft, String descricaoHard, String historicoProfissional, String historicoAcademico, String motivacao, int ano, char periodo, char semestre, LocalDateTime data, int idTG, String emailAluno, String emailOrientador) {
        this.identificacaoProjeto = identificacaoProjeto;
        this.empresaParceira = empresaParceira;
        this.problema = problema;
        this.solucao = solucao;
        this.linkRepositorio = linkRepositorio;
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
        this.contribuicoesPessoais = contribuicoesPessoais;
        this.descricaoSoft = descricaoSoft;
        this.descricaoHard = descricaoHard;
        this.historicoProfissional = historicoProfissional;
        this.historicoAcademico = historicoAcademico;
        this.motivacao = motivacao;
        this.ano = ano;
        this.periodo = periodo;
        this.semestre = semestre;
        this.data = data;
        this.idTG = idTG;
        this.emailAluno = emailAluno;
        this.emailOrientador = emailOrientador;
    }

    // --- Getters e Setters ---

    // 6. Get/Set de idSecao REMOVIDOS

    // 7. Get/Set de Data CORRIGIDOS
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    // (O resto dos seus Getters e Setters está correto)

    public String getIdentificacaoProjeto() {
        return identificacaoProjeto;
    }

    public void setIdentificacaoProjeto(String identificacaoProjeto) {
        this.identificacaoProjeto = identificacaoProjeto;
    }

    public String getEmpresaParceira() {
        return empresaParceira;
    }

    public void setEmpresaParceira(String empresaParceira) {
        this.empresaParceira = empresaParceira;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getLinkRepositorio() {
        return linkRepositorio;
    }

    public void setLinkRepositorio(String linkRepositorio) {
        this.linkRepositorio = linkRepositorio;
    }

    public String getTecnologiasUtilizadas() {
        return tecnologiasUtilizadas;
    }

    public void setTecnologiasUtilizadas(String tecnologiasUtilizadas) {
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
    }

    public String getContribuicoesPessoais() {
        return contribuicoesPessoais;
    }

    public void setContribuicoesPessoais(String contribuicoesPessoais) {
        this.contribuicoesPessoais = contribuicoesPessoais;
    }

    public String getDescricaoSoft() {
        return descricaoSoft;
    }

    public void setDescricaoSoft(String descricaoSoft) {
        this.descricaoSoft = descricaoSoft;
    }

    public String getDescricaoHard() {
        return descricaoHard;
    }

    public void setDescricaoHard(String descricaoHard) {
        this.descricaoHard = descricaoHard;
    }

    public String getHistoricoProfissional() {
        return historicoProfissional;
    }

    public void setHistoricoProfissional(String historicoProfissional) {
        this.historicoProfissional = historicoProfissional;
    }

    public String getHistoricoAcademico() {
        return historicoAcademico;
    }

    public void setHistoricoAcademico(String historicoAcademico) {
        this.historicoAcademico = historicoAcademico;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public char getPeriodo() {
        return periodo;
    }

    public void setPeriodo(char periodo) {
        this.periodo = periodo;
    }

    public char getSemestre() {
        return semestre;
    }

    public void setSemestre(char semestre) {
        this.semestre = semestre;
    }

    public int getIdTG() {
        return idTG;
    }

    public void setIdTG(int idTG) {
        this.idTG = idTG;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getEmailOrientador() {
        return emailOrientador;
    }

    public void setEmailOrientador(String emailOrientador) {
        this.emailOrientador = emailOrientador;
    }

    @Override
    public String toString() {
        return "Secao{" +
                // "idSecao=" + idSecao + // REMOVIDO
                "identificacaoProjeto='" + identificacaoProjeto + '\'' +
                ", empresaParceira='" + empresaParceira + '\'' +
                ", problema='" + problema + '\'' +
                ", solucao='" + solucao + '\'' +
                ", linkRepositorio='" + linkRepositorio + '\'' +
                ", tecnologiasUtilizadas='" + tecnologiasUtilizadas + '\'' +
                ", contribuicoesPessoais='" + contribuicoesPessoais + '\'' +
                ", descricaoSoft='" + descricaoSoft + '\'' +
                ", descricaoHard='" + descricaoHard + '\'' +
                ", historicoProfissional='" + historicoProfissional + '\'' +
                ", historicoAcademico='" + historicoAcademico + '\'' +
                ", motivacao='" + motivacao + '\'' +
                ", ano=" + ano +
                ", periodo=" + periodo +
                ", semestre=" + semestre +
                ", data=" + data +
                ", idTG=" + idTG +
                ", emailAluno='" + emailAluno + '\'' +
                ", emailOrientador='" + emailOrientador + '\'' +
                '}';
    }
}