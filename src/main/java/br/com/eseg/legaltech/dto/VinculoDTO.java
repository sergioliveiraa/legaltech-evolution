package br.com.eseg.legaltech.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record VinculoDTO(
        @NotNull(message = "clienteId é obrigatório") Long clienteId,
        @NotNull(message = "legaltechId é obrigatório") Long legaltechId,
        @NotNull(message = "dataInicio é obrigatória") LocalDate dataInicio
) {}