package br.org.serratec.dto.cliente;

import br.org.serratec.dto.EnderecoResponseDTO;

public class ClienteResponseDTO {

	private Long idCliente;
	private String login;
	private String email;
	private String cpf;
	private EnderecoResponseDTO endereco;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoResponseDTO getEnderecoResponseDTO() {
		return endereco;
	}

	public void setEnderecoResponseDTO(EnderecoResponseDTO endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}