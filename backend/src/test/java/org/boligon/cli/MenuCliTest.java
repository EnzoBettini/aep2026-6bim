package org.boligon.cli;

import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuCliTest {

    @Mock
    SolicitacaoService solicitacaoService;

    private MenuCli menuComEntrada(String entrada) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        return new MenuCli(solicitacaoService, scanner);
    }

    @Test
    void deveCadastrarESair() {
        Solicitacao criada = new Solicitacao("cid1", "Titulo", "Desc", "Centro", Categoria.ILUMINACAO);
        criada.setId("abc");
        when(solicitacaoService.criar(any(SolicitacaoRequest.class))).thenReturn(criada);

        menuComEntrada("1\ncid1\nTitulo\nDesc\nCentro\n1\n0\n").run();

        verify(solicitacaoService).criar(any(SolicitacaoRequest.class));
    }

    @Test
    void deveListarESair() {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        s.setId("1");
        when(solicitacaoService.listar()).thenReturn(List.of(s));

        menuComEntrada("2\n0\n").run();

        verify(solicitacaoService).listar();
    }

    @Test
    void deveExcluirESair() {
        menuComEntrada("5\n1\n0\n").run();

        verify(solicitacaoService).excluir("1");
    }
}
