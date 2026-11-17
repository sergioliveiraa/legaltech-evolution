package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vinculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "O cliente é obrigatório")
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "legaltech_id", nullable = false)
    @NotNull(message = "A legaltech é obrigatória")
    private Legaltech legaltech;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_vinculo", nullable = false, length = 20)
    @NotNull(message = "O status do vínculo é obrigatório")
    private StatusVinculo statusVinculo;

    public enum StatusVinculo {
        ATIVO, INATIVO, SUSPENSO, CANCELADO
    }
}