package br.org.serratec.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.model.Produto;
import br.org.serratec.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> buscarTodos() {
		return ResponseEntity.ok(produtoService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarProdutoPorId(@PathVariable("id") Long Id) {
		return ResponseEntity.ok(produtoService.buscarPorId(Id));
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestParam MultipartFile file, @RequestPart ProdutoDTO produtoDTO)
			throws IOException {
		return new ResponseEntity<>(produtoService.cadastrar(produtoDTO, file), HttpStatus.CREATED);
	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarPorFoto(@PathVariable Long id) {
		Produto produto = produtoService.produtoPorId(id);

		if (produto == null) {
			return ResponseEntity.notFound().build();
		}
		HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
		httpHeaders.add("content-type", "image/png");
		httpHeaders.add("content-length", String.valueOf(produto.getImagem().length));
		return new ResponseEntity<>(produto.getImagem(), httpHeaders, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO) {
		return ResponseEntity.ok(produtoService.alterar(id, produtoDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable("id") Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.ok("Produto deletado com sucesso.");
	}
}