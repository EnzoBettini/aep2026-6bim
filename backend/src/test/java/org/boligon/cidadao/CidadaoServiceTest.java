package org.boligon.cidadao;

import org.boligon.cidadao.domain.Cidadao;
import org.boligon.cidadao.dto.CidadaoRequest;
import org.boligon.cidadao.repository.CidadaoRepository;
import org.boligon.cidadao.service.CidadaoService;
import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.ValidacaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CidadaoServiceTest {

    @Mock
    CidadaoRepository repository;

    CidadaoService service;

    @BeforeEach
    void setUp() {
        service = new CidadaoService(repository);
    }

    @Test
    void deveCriarCidadao() {
        CidadaoRequest request = new CidadaoRequest();
        request.setNome("Maria");
        request.setEmail("Maria@Email.com");
        request.setTelefone("44999990000");

        when(repository.existsByEmailIgnoreCase("maria@email.com")).thenReturn(false);
        when(repository.save(any(Cidadao.class))).thenAnswer(inv -> inv.getArgument(0));

        Cidadao criado = service.criar(request);

        assertEquals("Maria", criado.getNome());
        assertEquals("maria@email.com", criado.getEmail());
    }

    @Test
    void deveRecusarEmailDuplicado() {
        CidadaoRequest request = new CidadaoRequest();
        request.setNome("Maria");
        request.setEmail("maria@email.com");

        when(repository.existsByEmailIgnoreCase("maria@email.com")).thenReturn(true);

        try {
            service.criar(request);
            fail();
        } catch (ValidacaoException ex) {
            assertEquals("Já existe um cidadão com este e-mail.", ex.getMessage());
        }
    }

    @Test
    void deveBuscarPorId() {
        Cidadao c = new Cidadao("João", "joao@email.com", null);
        c.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(c));

        assertEquals("1", service.buscarPorId("1").getId());
    }

    @Test
    void deveFalharAoBuscarInexistente() {
        when(repository.findById("x")).thenReturn(Optional.empty());

        try {
            service.buscarPorId("x");
            fail();
        } catch (EntidadeNaoEncontradaException ex) {
            assertEquals("Cidadão não encontrado.", ex.getMessage());
        }
    }

    @Test
    void deveListar() {
        when(repository.findAll()).thenReturn(List.of(new Cidadao("A", "a@b.com", null)));
        assertEquals(1, service.listar().size());
    }

    @Test
    void deveExcluir() {
        Cidadao existente = new Cidadao("A", "a@b.com", null);
        existente.setId("9");
        when(repository.findById("9")).thenReturn(Optional.of(existente));

        service.excluir("9");

        verify(repository).delete(existente);
    }
}
