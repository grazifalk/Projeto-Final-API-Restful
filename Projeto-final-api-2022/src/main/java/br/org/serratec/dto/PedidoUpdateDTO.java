package br.org.serratec.dto;

import java.time.LocalDate;

import br.org.serratec.model.StatusPedido;

public class PedidoUpdateDTO {

	private Long cliente;
	private LocalDate dataPedido;
	private StatusPedido status;

	public PedidoUpdateDTO() {
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

}
