package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    @Column(name = "status_documento", nullable = false, length = 30)
    @NotNull(message = "O status do documento é obrigatório")
    private String statusDocumento;

    @Column(name = "revisao_humana", nullable = false)
    private boolean revisaoHumana;

    @Column(name = "data_evento", nullable = false)
    @NotNull(message = "A data do evento é obrigatória")
    private LocalDateTime dataEvento;
}