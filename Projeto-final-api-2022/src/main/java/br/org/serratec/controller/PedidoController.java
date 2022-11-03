package br.org.serratec.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.exception.EmailException;
import br.org.serratec.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<PedidoDTO>> buscarTodos() {
		return ResponseEntity.ok(pedidoService.buscarTodos());
	}

	@GetMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long idPedido) {
		return ResponseEntity.ok(pedidoService.buscarPorId(idPedido));
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody PedidoDTO pedido) {
		return new ResponseEntity<>(pedidoService.cadastrar(pedido), HttpStatus.CREATED);
	}

	@PutMapping("/{idPedido}")
	public ResponseEntity<?> atualizar(@PathVariable Long idPedido, @RequestBody PedidoDTO pedido) {
		return ResponseEntity.ok(pedidoService.atualizar(idPedido, pedido));
	}

	@DeleteMapping("/{idPedido}")
	public ResponseEntity<?> deletar(@PathVariable Long idPedido) {
		pedidoService.deletar(idPedido);
		return ResponseEntity.ok("O pedido com o id: " + idPedido + " foi deletado com sucesso!");
	}

	@PutMapping("/{idPedido}/finalizacao")
	public ResponseEntity<String> finalizar(@PathVariable Long idPedido) throws MessagingException, EmailException {
		return ResponseEntity.ok(pedidoService.finalizar(idPedido));

	}

}