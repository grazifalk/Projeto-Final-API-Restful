
package br.org.serratec.controller;

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

import br.org.serratec.dto.EnderecoRequestDTO;
import br.org.serratec.service.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		return ResponseEntity.ok(enderecoService.buscarTodos());
	}

	@GetMapping("/id/{idEndereco}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idEndereco) {
		return ResponseEntity.ok(enderecoService.buscarPorId(idEndereco));
	}

	@GetMapping("/cep/{cep}")
	public ResponseEntity<?> buscarPorCep(@PathVariable String cep) {
		return ResponseEntity.ok(enderecoService.buscarPorCep(cep));
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody EnderecoRequestDTO enderecoRequestDTO) {
		return new ResponseEntity<>(enderecoService.cadastrar(enderecoRequestDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
		return ResponseEntity.ok(enderecoService.atualizar(id, enderecoRequestDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		enderecoService.deletar(id);
		return ResponseEntity.ok("O endereço com o id: " + id + " foi deletado com sucesso!");
	}

}
