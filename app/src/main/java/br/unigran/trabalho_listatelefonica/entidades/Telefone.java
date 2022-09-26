package br.unigran.trabalho_listatelefonica.entidades;

public class Telefone {
    private Integer id;
    private String nome;
    private String telefone;
    private String data_nascimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return (String.format("Nome: %s / Telefone: %s / Data de Nascimento: %s", nome, telefone, data_nascimento));
    }
}