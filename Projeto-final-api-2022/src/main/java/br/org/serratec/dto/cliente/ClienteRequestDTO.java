package br.org.serratec.dto.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.org.serratec.dto.EnderecoRequestDTO;

public class ClienteRequestDTO {

	private String email;
	private String login;
	private String nome;
	private String senha;
	private String cpf;
	private String telefone;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	private EnderecoRequestDTO enderecoRequestDTO;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnderecoRequestDTO getEnderecoRequestDTO() {
		return enderecoRequestDTO;
	}

	public void setEnderecoRequestDTO(EnderecoRequestDTO enderecoRequestDTO) {
		this.enderecoRequestDTO = enderecoRequestDTO;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + "\n E-mail: " + email + "\n CPF: " + cpf + "\n Telefone: " + telefone
				+ "\n Data de Nascimento: " + dataNascimento;
	}

}