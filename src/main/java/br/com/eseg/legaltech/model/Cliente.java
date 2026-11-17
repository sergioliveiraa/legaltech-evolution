package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O tipo de cliente é obrigatório")
    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private String tipoCliente;

    @NotBlank(message = "O porte do cliente é obrigatório")
    @Column(name = "porte_cliente", nullable = false, length = 30)
    private String porteCliente;

    @NotBlank(message = "A área jurídica principal é obrigatória")
    @Column(name = "area_juridica_principal", nullable = false, length = 50)
    private String areaJuridicaPrincipal;

    @NotBlank(message = "O canal de aquisição é obrigatório")
    @Column(name = "canal_aquisicao", nullable = false, length = 50)
    private String canalAquisicao;

    @NotNull(message = "A data de cadastro é obrigatória")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "localidade_id", nullable = false)
    @NotNull(message = "A localidade é obrigatória")
    private Localidade localidade;
}