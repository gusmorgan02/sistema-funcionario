
package entidade;

import java.sql.Date;

public class Funcionario {
    private int id;
    private String nome;
    private String cargo;
    private String departamento;
    private double salario;
    private int cargaHoraria;
    private Date dataAdmissao;

    public Funcionario() {
    }

    public Funcionario(String nome, String cargo, String departamento, double salario, int cargaHoraria, Date dataAdmissao) {
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
        this.dataAdmissao = dataAdmissao;
    }

    public Funcionario(int id, String nome, String cargo, String departamento, double salario, int cargaHoraria, Date dataAdmissao) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
        this.dataAdmissao = dataAdmissao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
}