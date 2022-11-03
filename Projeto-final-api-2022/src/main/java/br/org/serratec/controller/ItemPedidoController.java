package br.org.serratec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.service.ItemPedidoService;

@RestController
@RequestMapping("/itemPedidos")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping
	public ResponseEntity<List<ItemPedidoDTO>> buscarTodos() {
		return ResponseEntity.ok(itemPedidoService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idItemPedido) {
		return ResponseEntity.ok(itemPedidoService.buscarPorId(idItemPedido));
	}

//	@PostMapping
//	public ResponseEntity<?> cadastrar(@Valid @RequestBody ItemPedidoDTO itemPedidoDTO) {
//		return new ResponseEntity<>(itemPedidoService.cadastrar(itemPedidoDTO), HttpStatus.CREATED);
//	}

//	@PutMapping("/{id}")
//	public ResponseEntity<?> atualizar(
//			@PathVariable Long id,
//			@RequestBody ItemPedidoDTO pedido) {
//		return ResponseEntity.ok(itemPedidoService.atualizar(id, pedido));
//	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deletar(@PathVariable Long id) {
//		itemPedidoService.deletar(id);
//		return ResponseEntity.ok("O item com o id: " + id + " foi deletado com sucesso!");
//	}

}