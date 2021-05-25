package com.jlcb.minhasfinancas.api.resource.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice /* Estamos dizendo que essa classe é um componente especializado em tratar exceções */
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	 * Essa é a exceção lançada quando alguma validação de um argumento anotado com @Valid falha
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<AtributoErroMensagem> errors = getErros(ex);
		
		RequisicaoErroResposta errorResponse = getErroResposta(status, errors);
        
        return new ResponseEntity<>(errorResponse, status);
	}
	
	/* Passando o erro da requisição */
    private RequisicaoErroResposta getErroResposta(HttpStatus status, List<AtributoErroMensagem> errors) {
        return new RequisicaoErroResposta("A requisição possui campos inválidos", status.value(),
                status.getReasonPhrase(), errors);
    }
	
	
	/* Lista de erros dos atributos */
	private List<AtributoErroMensagem> getErros(MethodArgumentNotValidException ex) {
	    return ex.getBindingResult().getFieldErrors().stream()
	            .map(error -> new AtributoErroMensagem(error.getField(), error.getRejectedValue(), error.getDefaultMessage()))
	            .collect(Collectors.toList());
	}
}
