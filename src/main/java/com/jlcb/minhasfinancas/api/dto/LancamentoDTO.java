package com.jlcb.minhasfinancas.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class LancamentoDTO {
	
	private Long id;
	
	@NotBlank(message = "Informe uma descrição")
	private String descricao;

	@NotNull(message = "Informe um mês")
	@Min(value = 1, message = "O mês não pode ser menor que 1")
	@Max(value = 12, message = "O mês não pode ser maior que 12")
	private Integer mes;

	@NotNull(message = "Informe um ano")
	@Digits(integer = 4, fraction = 0, message = "O ano informado está no formato inváldio")
	private Integer ano;

	@NotNull(message = "Informe um valor")
	@Min(value = 1, message = "O valor deve ser maior que 1")
	private BigDecimal valor;

	@NotBlank(message = "Informe o tipo do lançamento")
	private String tipo;

	private String status;

	@NotNull(message = "Infome um usuário")
	private Long usuario;
}
