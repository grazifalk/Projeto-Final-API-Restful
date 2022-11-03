package br.org.serratec.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarCategorias() {
		return ResponseEntity.ok(categoriaService.buscarCategorias());
	}

	@GetMapping("/{idCategoria}")
	public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long idCategoria) {
		return ResponseEntity.ok(categoriaService.buscarPorId(idCategoria));
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> criarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return new ResponseEntity<>(categoriaService.criarCategoria(categoriaDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{idCategoria}")
	public ResponseEntity<?> alterarCategoria(@PathVariable Long idCategoria,
			@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return ResponseEntity.ok(categoriaService.alterarCategoria(idCategoria, categoriaDTO));
	}

	@DeleteMapping("/{idCategoria}")
	public ResponseEntity<?> deletarCategoria(@PathVariable Long idCategoria) {
		categoriaService.deletarCategoria(idCategoria);
		return ResponseEntity.ok("Categoria deletada com sucesso.");
	}

}