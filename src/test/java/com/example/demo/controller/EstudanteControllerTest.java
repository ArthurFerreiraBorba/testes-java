package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.service.EstudanteService;
import com.example.demo.service.TurmaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class EstudanteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EstudanteService estudanteService;

    @MockBean
    private TurmaService turmaService;

    @Test
    void listarEstudantes() throws Exception {
        mockMvc.perform(
                get("/estudantes")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void cadastrarEstudante() throws Exception {
        when(estudanteService.cadastrarEstudante("nome", "11.22.33")).thenReturn(new Estudante(1L, "nome", "11.22.33", Collections.emptyList()));

        mockMvc.perform(
                        post("/estudantes")
                                .content("{\n" +
                                            "    \"nome\": \"nome\",\n" +
                                            "    \"matricula\": \"11.22.33\"\n" +
                                            "}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("nome"))
                .andExpect(jsonPath("$.matricula").value("11.22.33"));
    }

    @Test
    void atualizarEstudante() throws Exception {
        when(estudanteService.atualizarEstudante(1L, "nome", "11.22.33")).thenReturn(new Estudante(1L, "nome", "11.22.33", Collections.emptyList()));

        mockMvc.perform(
                        put("/estudantes/{id}", 1)
                                .content("{\n" +
                                        "    \"nome\": \"nome\",\n" +
                                        "    \"matricula\": \"11.22.33\"\n" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("nome"))
                .andExpect(jsonPath("$.matricula").value("11.22.33"));
    }
}