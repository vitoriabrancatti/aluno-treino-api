package com.academia.domain;

public abstract class Pessoa {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private int idade;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, String cpf, String email, int idade, String tipo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
        this.getTipo();
    }

    private void getTipo() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getTipo'");
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }



}
