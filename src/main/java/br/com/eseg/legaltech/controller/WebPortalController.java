package br.com.eseg.legaltech.controller;

import br.com.eseg.legaltech.repository.ContratoRepository;
import br.com.eseg.legaltech.repository.DocumentoRepository;
import br.com.eseg.legaltech.repository.VinculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebPortalController {

    private final VinculoRepository vinculoRepository;
    private final ContratoRepository contratoRepository;
    private final DocumentoRepository documentoRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        model.addAttribute("perfis", authentication.getAuthorities());
        model.addAttribute("totalVinculos", vinculoRepository.count());
        model.addAttribute("totalContratos", contratoRepository.count());
        model.addAttribute("totalDocumentos", documentoRepository.count());
        return "dashboard";
    }

    @GetMapping("/admin/gestao")
    public String gestaoAdministrativa(Model model) {
        model.addAttribute("contratos", contratoRepository.findAll());
        return "admin-gestao";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}