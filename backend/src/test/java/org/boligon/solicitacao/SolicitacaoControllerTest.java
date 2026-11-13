package org.boligon.solicitacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.GlobalExceptionHandler;
import org.boligon.exception.ValidacaoException;
import org.boligon.solicitacao.controller.SolicitacaoController;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.domain.StatusSolicitacao;
import org.boligon.solicitacao.dto.MudarStatusRequest;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SolicitacaoController.class)
@Import(GlobalExceptionHandler.class)
class SolicitacaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SolicitacaoService solicitacaoService;

    @Test
    void postSolicitacoesDeveRetornar201() throws Exception {
        Solicitacao criada = new Solicitacao("cid1", "Buraco", "Desc", "Centro", Categoria.BURACO);
        criada.setId("s1");
        when(solicitacaoService.criar(any(SolicitacaoRequest.class))).thenReturn(criada);

        SolicitacaoRequest body = new SolicitacaoRequest();
        body.setCidadaoId("cid1");
        body.setTitulo("Buraco");
        body.setDescricao("Desc");
        body.setBairro("Centro");
        body.setCategoria(Categoria.BURACO);

        mockMvc.perform(post("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("s1"))
                .andExpect(jsonPath("$.status").value("ABERTA"));
    }

    @Test
    void postSolicitacoesDeveRetornar400() throws Exception {
        when(solicitacaoService.criar(any()))
                .thenThrow(new ValidacaoException("O título é obrigatório."));

        mockMvc.perform(post("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("O título é obrigatório."));
    }

    @Test
    void getSolicitacoesDeveListar() throws Exception {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        s.setId("1");
        when(solicitacaoService.listar()).thenReturn(List.of(s));

        mockMvc.perform(get("/api/solicitacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void getSolicitacaoPorIdDeveRetornar404() throws Exception {
        when(solicitacaoService.buscarPorId("x"))
                .thenThrow(new EntidadeNaoEncontradaException("Solicitação não encontrada."));

        mockMvc.perform(get("/api/solicitacoes/x"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Solicitação não encontrada."));
    }

    @Test
    void putSolicitacaoDeveAtualizar() throws Exception {
        Solicitacao s = new Solicitacao("cid1", "Novo", "d", "b", Categoria.PODA);
        s.setId("1");
        when(solicitacaoService.atualizar(eq("1"), any(SolicitacaoRequest.class))).thenReturn(s);

        SolicitacaoRequest body = new SolicitacaoRequest();
        body.setCidadaoId("cid1");
        body.setTitulo("Novo");
        body.setDescricao("d");
        body.setBairro("b");
        body.setCategoria(Categoria.PODA);
        body.setStatus(StatusSolicitacao.EM_ANDAMENTO);

        mockMvc.perform(put("/api/solicitacoes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Novo"));
    }

    @Test
    void patchStatusDeveRetornar200() throws Exception {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        s.setId("1");
        s.mudarStatus(StatusSolicitacao.EM_ANDAMENTO, "Em análise");
        when(solicitacaoService.mudarStatus(eq("1"), any(MudarStatusRequest.class))).thenReturn(s);

        MudarStatusRequest body = new MudarStatusRequest();
        body.setStatus(StatusSolicitacao.EM_ANDAMENTO);
        body.setObservacao("Em análise");

        mockMvc.perform(patch("/api/solicitacoes/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"));
    }

    @Test
    void patchStatusDeveRetornar400QuandoTransicaoInvalida() throws Exception {
        when(solicitacaoService.mudarStatus(eq("1"), any(MudarStatusRequest.class)))
                .thenThrow(new ValidacaoException("Não é permitido reabrir uma solicitação resolvida."));

        MudarStatusRequest body = new MudarStatusRequest();
        body.setStatus(StatusSolicitacao.ABERTA);

        mockMvc.perform(patch("/api/solicitacoes/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem")
                        .value("Não é permitido reabrir uma solicitação resolvida."));
    }

    @Test
    void getHistoricoDeveRetornar200() throws Exception {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.ILUMINACAO);
        when(solicitacaoService.listarHistorico("1")).thenReturn(s.getHistoricoStatus());

        mockMvc.perform(get("/api/solicitacoes/1/historico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ABERTA"));
    }

    @Test
    void getHistoricoDeveRetornar404() throws Exception {
        when(solicitacaoService.listarHistorico("x"))
                .thenThrow(new EntidadeNaoEncontradaException("Solicitação não encontrada."));

        mockMvc.perform(get("/api/solicitacoes/x/historico"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Solicitação não encontrada."));
    }

    @Test
    void deleteSolicitacaoDeveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/solicitacoes/1"))
                .andExpect(status().isNoContent());

        verify(solicitacaoService).excluir("1");
    }
}
