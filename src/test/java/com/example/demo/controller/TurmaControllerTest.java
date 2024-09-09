package com.example.demo.controller;

import com.example.demo.controller.dto.RequestTurmaEstudanteId;
import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.EstudanteRepository;
import com.example.demo.database.repositories.TurmaRepository;
import com.example.demo.service.EstudanteService;
import com.example.demo.service.TurmaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/TurmaControllerTest.sql", executionPhase = BEFORE_TEST_CLASS)
class TurmaControllerTest {

    @Autowired
    MockMvc mockMvc;

    Turma turma = new Turma(1L, "Estatistica", new ArrayList<>());
    Estudante estudante = new Estudante(1L, "Jefferson", "1234", new ArrayList<>());

    @Test
    void testAdicionarEstudanteNaTurma() throws Exception {

        mockMvc.perform(
                post("/turmas/adicionar/estudantes")
                        .content(
                                "{\"turmaId\": 1," +
                                "\"estudanteId\": 1}"
                        )
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(estudante.getNome()))
                .andExpect(jsonPath("$.matricula").value(estudante.getMatricula()));
    }

    @Test
    void testRemoverEstudanteDaTurma() throws Exception {

        mockMvc.perform(
                        post("/turmas/remover/estudantes")
                                .content(
                                        "{\"turmaId\": 1," +
                                        "\"estudanteId\": 1}"
                                )
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(estudante.getNome()))
                .andExpect(jsonPath("$.matricula").value(estudante.getMatricula()));
    }

    @Test
    void listarTurmas() throws Exception {

        mockMvc.perform(
                get("/turmas")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value(turma.getNome()));
    }

    @Test
    void buscarTurmaPorId() throws Exception {

        mockMvc.perform(
                        get("/turmas/{id}", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value(turma.getNome()));
    }

    @Test
    void cadastrarTurma() throws Exception {

        mockMvc.perform(
                        post("/turmas")
                                .content("{\"nome\": \"test\"}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("test"));
    }

}