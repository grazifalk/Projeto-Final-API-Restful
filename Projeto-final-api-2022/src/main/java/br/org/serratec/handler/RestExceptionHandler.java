package br.org.serratec.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.org.serratec.exception.ResourceBadRequestException;
import br.org.serratec.exception.ResourceNotFoundException;
import br.org.serratec.model.erro.MensagemError;
import br.org.serratec.util.ConversorDeData;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex) {

		String dataHora = ConversorDeData.converterDateParaDataEHora(new Date());

		MensagemError erro = new MensagemError(dataHora, 404, "Not Found", ex.getMessage());

		return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceBadRequestException.class)
	public ResponseEntity<?> handlerBadRequestException(ResourceBadRequestException ex) {

		String dataHora = ConversorDeData.converterDateParaDataEHora(new Date());
		MensagemError erro = new MensagemError(dataHora, 400, "Bad Request", ex.getMessage());

		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerBadRequestException(Exception ex) {
		String dataHora = ConversorDeData.converterDateParaDataEHora(new Date());
		MensagemError erro = new MensagemError(dataHora, 500, "Internal Server Error", ex.getMessage());

		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
