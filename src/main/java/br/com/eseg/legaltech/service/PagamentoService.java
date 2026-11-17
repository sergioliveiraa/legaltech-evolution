package br.com.eseg.legaltech.service;

import br.com.eseg.legaltech.exception.BusinessRuleException;
import br.com.eseg.legaltech.model.Pagamento;
import br.com.eseg.legaltech.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final VinculoService vinculoService;

    public PagamentoService(PagamentoRepository pagamentoRepository, VinculoService vinculoService) {
        this.pagamentoRepository = pagamentoRepository;
        this.vinculoService = vinculoService;
    }

    @Transactional
    public Pagamento registrarPagamento(Pagamento pagamento) {
        vinculoService.validarDataFato(pagamento.getVinculo().getId(), pagamento.getDataPagamento());

        if ("ASSINATURA".equalsIgnoreCase(pagamento.getTipoReceita())) {
            if (pagamento.getContrato() == null || pagamento.getContrato().getId() == null) {
                throw new BusinessRuleException("Violação da RN-05: Pagamento do tipo ASSINATURA deve obrigatoriamente referenciar um Contrato.");
            }
        }

        if ("ESTORNO".equalsIgnoreCase(pagamento.getStatusPagamento())) {
            if (pagamento.getPagamentoOrigem() == null || pagamento.getPagamentoOrigem().getId() == null) {
                throw new BusinessRuleException("Violação da RN-05: Registros de estorno devem citar obrigatoriamente o pagamento de origem.");
            }
        }

        return pagamentoRepository.save(pagamento);
    }
}