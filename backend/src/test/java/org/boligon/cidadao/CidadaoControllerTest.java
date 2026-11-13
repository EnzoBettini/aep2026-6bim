package org.boligon.cidadao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.boligon.cidadao.controller.CidadaoController;
import org.boligon.cidadao.domain.Cidadao;
import org.boligon.cidadao.dto.CidadaoRequest;
import org.boligon.cidadao.service.CidadaoService;
import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.GlobalExceptionHandler;
import org.boligon.exception.ValidacaoException;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CidadaoController.class)
@Import(GlobalExceptionHandler.class)
class CidadaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CidadaoService cidadaoService;

    @MockBean
    SolicitacaoService solicitacaoService;

    @Test
    void postCidadaosDeveRetornar201() throws Exception {
        Cidadao criado = new Cidadao("Maria", "maria@email.com", "41999998888");
        criado.setId("c1");
        when(cidadaoService.criar(any(CidadaoRequest.class))).thenReturn(criado);

        CidadaoRequest body = new CidadaoRequest();
        body.setNome("Maria");
        body.setEmail("maria@email.com");
        body.setTelefone("41999998888");

        mockMvc.perform(post("/api/cidadaos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("c1"))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void postCidadaosDeveRetornar400QuandoValidacaoFalha() throws Exception {
        when(cidadaoService.criar(any(CidadaoRequest.class)))
                .thenThrow(new ValidacaoException("O nome é obrigatório."));

        CidadaoRequest body = new CidadaoRequest();
        body.setEmail("x@y.com");

        mockMvc.perform(post("/api/cidadaos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("O nome é obrigatório."));
    }

    @Test
    void getCidadaosDeveListar() throws Exception {
        Cidadao c = new Cidadao("João", "j@e.com", null);
        c.setId("1");
        when(cidadaoService.listar()).thenReturn(List.of(c));

        mockMvc.perform(get("/api/cidadaos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void getCidadaoPorIdDeveRetornar200() throws Exception {
        Cidadao c = new Cidadao("João", "j@e.com", null);
        c.setId("1");
        when(cidadaoService.buscarPorId("1")).thenReturn(c);

        mockMvc.perform(get("/api/cidadaos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void getCidadaoPorIdDeveRetornar404() throws Exception {
        when(cidadaoService.buscarPorId("x"))
                .thenThrow(new EntidadeNaoEncontradaException("Cidadão não encontrado."));

        mockMvc.perform(get("/api/cidadaos/x"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Cidadão não encontrado."));
    }

    @Test
    void getSolicitacoesDoCidadaoDeveRetornar200() throws Exception {
        Solicitacao s = new Solicitacao("1", "t", "d", "b", Categoria.LIXO);
        s.setId("s1");
        when(solicitacaoService.listarPorCidadaoId("1")).thenReturn(List.of(s));

        mockMvc.perform(get("/api/cidadaos/1/solicitacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("s1"));
    }

    @Test
    void putCidadaoDeveAtualizar() throws Exception {
        Cidadao atualizado = new Cidadao("Novo", "n@e.com", "11");
        atualizado.setId("1");
        when(cidadaoService.atualizar(eq("1"), any(CidadaoRequest.class))).thenReturn(atualizado);

        CidadaoRequest body = new CidadaoRequest();
        body.setNome("Novo");
        body.setEmail("n@e.com");
        body.setTelefone("11");

        mockMvc.perform(put("/api/cidadaos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void deleteCidadaoDeveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/cidadaos/1"))
                .andExpect(status().isNoContent());

        verify(cidadaoService).excluir("1");
    }

    @Test
    void deleteCidadaoDeveRetornar404() throws Exception {
        doThrow(new EntidadeNaoEncontradaException("Cidadão não encontrado."))
                .when(cidadaoService).excluir("x");

        mockMvc.perform(delete("/api/cidadaos/x"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Cidadão não encontrado."));
    }
}
