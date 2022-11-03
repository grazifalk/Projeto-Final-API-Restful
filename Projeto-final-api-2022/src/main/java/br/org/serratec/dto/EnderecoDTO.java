package br.org.serratec.dto;

import br.org.serratec.model.Endereco;

public class EnderecoDTO {

	private Long idEndereco;
	// @ApiModelProperty(value = "Identificador de uma rua")
	private String cep;
	// @ApiModelProperty(value = "Identifica uma rua")
	private String logradouro;
	private String bairro;
	private String localidade;
	private Integer numero;
	private String complemento;
	// @ApiModelProperty(value = "Identifica o estado")
	private String uf;

	public EnderecoDTO() {
	}

	public EnderecoDTO(Endereco endereco) {
		this.cep = endereco.getCep();
		this.logradouro = endereco.getRua();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.localidade = endereco.getCidade();
		this.uf = endereco.getUf();
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereço) {
		this.idEndereco = idEndereço;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Endereco: \n" + "Rua: " + logradouro + "\n Número: " + numero + "\n Bairro: " + bairro
				+ "\n Complemento:" + complemento + "\n Cidade: " + localidade + "\n UF:" + uf + "\n CEP: " + cep + "]";
	}

}