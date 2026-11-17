package br.com.eseg.legaltech;

import br.com.eseg.legaltech.model.Contrato;
import br.com.eseg.legaltech.model.ProcessoJuridico;
import br.com.eseg.legaltech.repository.ContratoRepository;
import br.com.eseg.legaltech.repository.DocumentoRepository;
import br.com.eseg.legaltech.repository.VinculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LegaltechIntegracaoTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private VinculoRepository vinculoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("1. Deve carregar banco H2 com schema.sql e data.sql via JPA")
    void deveCarregarBancoEPersistirDadosIniciais() {
        assertTrue(vinculoRepository.count() > 0, "Deve haver vínculos carregados do data.sql");
        assertNotNull(contratoRepository.findAll(), "Repositório de contratos deve responder via JPA");
        assertNotNull(documentoRepository.findAll(), "Repositório de documentos deve responder via JPA");
    }

    @Test
    @DisplayName("2. Deve calcular corretamente regras de domínio em Contrato e ProcessoJuridico")
    void deveValidarRegrasDeDominio() {
        Contrato ativo = Contrato.builder().dataInicio(LocalDate.now()).build();
        Contrato cancelado = Contrato.builder().dataInicio(LocalDate.now()).dataCancelamento(LocalDate.now()).build();

        assertFalse(ativo.isCancelamentoConfirmado());
        assertTrue(cancelado.isCancelamentoConfirmado());

        ProcessoJuridico riscoBaixo = ProcessoJuridico.builder().scoreRiscoPerda(0.15).build();
        ProcessoJuridico riscoCritico = ProcessoJuridico.builder().scoreRiscoPerda(0.85).build();

        assertEquals("BAIXO", riscoBaixo.getFaixaRiscoCalculada());
        assertEquals("CRITICO", riscoCritico.getFaixaRiscoCalculada());
    }

    @Test
    @DisplayName("3. Deve redirecionar usuário não autenticado para /login")
    void deveRedirecionarNaoAutenticadoParaLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", endsWith("/login")));
    }

    @Test
    @DisplayName("4. Perfil OPERADOR deve acessar /dashboard mas receber 403 em /admin/gestao")
    void deveBloquearOperadorNaAreaAdmin() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/login")
                        .user("operador@legaltech.com")
                        .password("oper123"))
                .andExpect(authenticated().withUsername("operador@legaltech.com").withRoles("OPERADOR"))
                .andExpect(redirectedUrl("/dashboard"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession();

        mockMvc.perform(get("/dashboard").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));

        mockMvc.perform(get("/admin/gestao").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("5. Perfil ADMIN deve acessar /dashboard e /admin/gestao com sucesso")
    void devePermitirAdminNaAreaRestrita() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/login")
                        .user("admin@legaltech.com")
                        .password("admin123"))
                .andExpect(authenticated().withUsername("admin@legaltech.com").withRoles("ADMIN"))
                .andExpect(redirectedUrl("/dashboard"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession();

        mockMvc.perform(get("/admin/gestao").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-gestao"))
                .andExpect(model().attributeExists("contratos"));
    }

    @Test
    @DisplayName("6. Deve realizar logout e invalidar a sessão")
    void deveRealizarLogout() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}