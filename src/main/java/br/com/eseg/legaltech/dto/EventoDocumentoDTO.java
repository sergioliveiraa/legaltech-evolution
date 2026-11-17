package br.com.eseg.legaltech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoDocumentoDTO(
        @NotBlank(message = "statusDocumento é obrigatório") String statusDocumento,
        @NotNull(message = "revisaoHumana deve ser informado") Boolean revisaoHumana
) {}