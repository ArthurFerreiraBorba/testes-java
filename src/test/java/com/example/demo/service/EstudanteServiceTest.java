package com.example.demo.service;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.repositories.EstudanteRepository;
import com.example.demo.service.EstudanteService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudanteServiceTest {

    @Mock
    private EstudanteRepository estudanteRepository;

    @InjectMocks
    private EstudanteService estudanteService;

    @Test
    void cadastrarEstudante() {
        Estudante resposta = estudanteService.cadastrarEstudante("arthur", "matricula");

        verify(estudanteRepository).save(any(Estudante.class));
        assertNotNull(resposta);
        assertEquals(resposta.getNome(), "arthur");
        assertEquals(resposta.getMatricula(), "matricula");
    }

    @Test
    void listarEstudantes() {
        Estudante estudante = new Estudante(1L, "nome", "11.22.33", Collections.emptyList());
        List<Estudante> lista = new ArrayList<>(List.of(estudante));
        when(estudanteRepository.findAll()).thenReturn(lista);

        List<Estudante> resposta = estudanteService.listarEstudantes();

        verify(estudanteRepository).findAll();
        assertEquals(lista, resposta);
        assertEquals(lista.size(), resposta.size());
    }

    @Test
    void buscarEstudantePorId() {
        Estudante estudante = new Estudante(1L, "nome", "11.22.33", Collections.emptyList());
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));

        Estudante resposta = estudanteService.buscarEstudantePorId(1L);

        verify(estudanteRepository).findById(1L);
        assertNotNull(resposta);
        assertEquals(estudante, resposta);
    }

    @Test
    void atualizarEstudante() {
        Estudante estudante = new Estudante(1L, "nome", "11.22.33", Collections.emptyList());
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));


        Estudante resposta = estudanteService.atualizarEstudante(1L, "novo", "11.22.33");

        verify(estudanteRepository).save(any(Estudante.class));
        assertNotNull(resposta);
        assertEquals(resposta.getNome(), "novo");
        assertEquals(resposta.getMatricula(), "11.22.33");
    }
}