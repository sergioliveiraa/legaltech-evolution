package br.com.eseg.legaltech.service;

import br.com.eseg.legaltech.exception.BusinessRuleException;
import br.com.eseg.legaltech.exception.ResourceNotFoundException;
import br.com.eseg.legaltech.model.Cliente;
import br.com.eseg.legaltech.model.Legaltech;
import br.com.eseg.legaltech.model.Vinculo;
import br.com.eseg.legaltech.repository.ClienteRepository;
import br.com.eseg.legaltech.repository.LegaltechRepository;
import br.com.eseg.legaltech.repository.VinculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VinculoService {

    private final VinculoRepository vinculoRepository;
    private final ClienteRepository clienteRepository;
    private final LegaltechRepository legaltechRepository;

    public VinculoService(VinculoRepository vinculoRepository, ClienteRepository clienteRepository, LegaltechRepository legaltechRepository) {
        this.vinculoRepository = vinculoRepository;
        this.clienteRepository = clienteRepository;
        this.legaltechRepository = legaltechRepository;
    }

    @Transactional
    public Vinculo criarVinculo(Long clienteId, Long legaltechId, LocalDate dataInicio) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado ID: " + clienteId));

        Legaltech legaltech = legaltechRepository.findById(legaltechId)
                .orElseThrow(() -> new ResourceNotFoundException("Legaltech não encontrada ID: " + legaltechId));

        if (dataInicio.isBefore(cliente.getDataCadastro())) {
            throw new BusinessRuleException("A data de início do vínculo não pode ser anterior à data de cadastro do cliente (" + cliente.getDataCadastro() + ").");
        }

        Vinculo vinculo = Vinculo.builder()
                .cliente(cliente)
                .legaltech(legaltech)
                .dataInicio(dataInicio)
                .statusVinculo(Vinculo.StatusVinculo.ATIVO)
                .build();

        return vinculoRepository.save(vinculo);
    }

    public void validarDataFato(Long vinculoId, LocalDate dataFato) {
        Vinculo vinculo = vinculoRepository.findById(vinculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado ID: " + vinculoId));

        if (dataFato.isBefore(vinculo.getDataInicio())) {
            throw new BusinessRuleException(String.format(
                    "Violação da RN-02: O fato (%s) possui data anterior ao início do vínculo (%s).",
                    dataFato, vinculo.getDataInicio()
            ));
        }

        if (vinculo.getDataFim() != null && dataFato.isAfter(vinculo.getDataFim())) {
            throw new BusinessRuleException("O fato possui data posterior ao encerramento do vínculo.");
        }
    }

    public List<Vinculo> listarTodos() {
        return vinculoRepository.findAll();
    }

    public Vinculo buscarPorId(Long id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado ID: " + id));
    }
}