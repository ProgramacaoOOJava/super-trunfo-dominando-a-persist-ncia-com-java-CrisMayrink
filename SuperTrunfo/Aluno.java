package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author cmaya
 */
@Entity
@Table(name = "ALUNO")
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findByMatricula", query = "SELECT a FROM Aluno a WHERE a.matricula = :matricula"),
    @NamedQuery(name = "Aluno.findByNome", query = "SELECT a FROM Aluno a WHERE a.nome = :nome"),
    @NamedQuery(name = "Aluno.findByEntrada", query = "SELECT a FROM Aluno a WHERE a.entrada = :entrada")})

public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MATRICULA")
    private String matricula;
    
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "ENTRADA")
    private int entrada;
    
    //atributos extra para o jogo
    private int inteligencia;
    private int forca;
    private int criatividade;
    private boolean lendaria; // indica se é carta lendária
    
    public Aluno() {
    }

    public Aluno(String matricula) {
        this.matricula = matricula;
    }

    public Aluno(String matricula, String nome, int entrada, int inteligencia, int forca, int criatividade, boolean lendaria) {
        this.matricula = matricula;
        this.nome = nome;
        this.entrada = entrada;
        this.inteligencia = inteligencia;
        this.forca = forca;
        this.criatividade = criatividade;
        this.lendaria = lendaria;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getCriatividade() {
        return criatividade;
    }

    public void setCriatividade(int criatividade) {
        this.criatividade = criatividade;
    }

    
    public void setLendaria(boolean lendaria) {
        this.lendaria = lendaria;
    }


//getters e setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
  
    }

    //verifica se a carta é lendária
    public boolean isLendaria() {
        return entrada == 2030;
    }

    @Override
        public int hashCode() {
            int hash = 0;
            hash += (matricula != null ? matricula.hashCode() : 0);
            return hash;
        }

    @Override
   public boolean equals(Object obj) {
       if (this == obj) return true;
       if (!(obj instanceof Aluno)) return false;
       Aluno other = (Aluno) obj;
       return matricula != null && matricula.equals(other.matricula);
   }

    @Override
    public String toString() {
        if (isLendaria()) {
            return 
                   """

                   CARTA LENDÁRIA
                    Matricula: """ + matricula + "\n" +
                   "Nome: " + nome + "\n" +
                   "Entrada: " + entrada + "\n" +
                   "Inteligência: " + inteligencia + "\n" +
                   "Força: " + forca + "\n" +
                   "Criatividade: " + criatividade + "\n" +
                   "============================\n";
        }
    return matricula + " | " + nome + " | Entrada: " + entrada +
        " | Int: " + inteligencia +
        " | Força: " + forca +
        " | Criatividade: " + criatividade;

    }       
}
