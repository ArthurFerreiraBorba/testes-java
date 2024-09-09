package com.example.demo.service;

import com.example.demo.controller.dto.RequestTurmaEstudanteId;
import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.TurmaRepository;
import com.example.demo.service.TurmaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TurmaServiceTest {

    @MockBean
    private TurmaRepository turmaRepository;

    @MockBean
    private EstudanteService estudanteService;

    @Autowired
    private TurmaService turmaService;

    @Test
    void cadastrarTurma() {
        Turma resposta = turmaService.cadastrarTurma("turma");

        verify(turmaRepository).save(any(Turma.class));
        assertNotNull(resposta);
        assertEquals(resposta.getNome(), "turma");
    }

    @Test
    void listarTurmas() {
        Turma turma = new Turma(1L, "turma", Collections.emptyList());
        List<Turma> lista = new ArrayList<>(List.of(turma));
        when(turmaRepository.findAll()).thenReturn(lista);

        List<Turma> resposta = turmaService.listarTurmas();

        verify(turmaRepository).findAll();
        assertEquals(lista, resposta);
        assertEquals(lista.size(), resposta.size());
    }

    @Test
    void buscarTurmaPorId() {
        Turma turma = new Turma(1L, "turma", Collections.emptyList());
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));

        Turma resposta = turmaService.buscarTurmaPorId(1L);

        verify(turmaRepository).findById(1L);
        assertNotNull(resposta);
        assertEquals(turma, resposta);
    }

    @Test
    void atualizarTurma() {
        Turma turma = new Turma(1L, "turma", Collections.emptyList());
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));

        Turma resposta = turmaService.atualizarTurma(1L, "novo");

        verify(turmaRepository).save(any(Turma.class));
        assertNotNull(resposta);
        assertEquals(resposta.getNome(), "novo");
    }

    @Test
    void adicionarEstudanteNaTurma() {
        Turma turma = new Turma(1L, "turma", Collections.emptyList());
        Estudante estudante = new Estudante(1L, "nome", "11.22.33", new ArrayList<>());
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(estudanteService.buscarEstudantePorId(1L)).thenReturn(estudante);

        Estudante resposta = turmaService.adicionarEstudanteNaTurma(new RequestTurmaEstudanteId(1L, 1L));

        assertNotNull(resposta);
        assertEquals(resposta, estudante);
    }

    @Test
    void removerEstudanteDaTurma() {
        Turma turma = new Turma(1L, "turma", Collections.emptyList());
        Estudante estudante = new Estudante(1L, "nome", "11.22.33", new ArrayList<>());
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(estudanteService.buscarEstudantePorId(1L)).thenReturn(estudante);

        Estudante resposta = turmaService.removerEstudanteDaTurma(new RequestTurmaEstudanteId(1L, 1L));

        assertNotNull(resposta);
        assertEquals(resposta, estudante);
    }
}