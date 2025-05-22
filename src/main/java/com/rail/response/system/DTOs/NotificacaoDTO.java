package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificacaoDTO {

    @Schema(examples = "123")
    private Integer id;

    @NotNull
    @Size(max = 4000)
    @JsonProperty("conteudo")
    @Schema(examples = "Temperatura acima do limite em setor 5.")
    private String conteudo;

    @Size(max = 200)
    @JsonProperty("contato")
    @Schema(examples = "+55 11 99999-8888")
    private String contato;

    @JsonProperty("data_criacao")
    @Schema(examples = "2024-12-01")
    private LocalDate dataCriacao;

    @JsonProperty("data_atualizacao")
    @Schema(examples = "2024-12-10")
    private LocalDate dataAtualizacao;

    @NotNull(message = "Título é obrigatório")
    @Size(max = 100)
    @JsonProperty("titulo")
    @Schema(examples = "Alerta de Temperatura")
    private String titulo;

    @Size(max = 100)
    @JsonProperty("tipo_alerta")
    @Schema(examples = "Crítico")
    private String tipoAlerta;

    @NotNull(message = "Tipo de notificação é obrigatório")
    @Size(max = 100)
    @JsonProperty("tipo_notificacao")
    @Schema(examples = "Email")
    private String tipoNotificacao;

    @Size(max = 200)
    @JsonProperty("operador")
    @Schema(examples = "joao.silva")
    private String operador;

    @Size(max = 200)
    @JsonProperty("estacao")
    @Schema(examples = "Estação Norte")
    private String estacao;

    @JsonProperty("criticidade")
    @Schema(examples = "5")
    private Integer criticidade;

    @JsonProperty("prioridade")
    @Schema(examples = "1")
    private Integer prioridade;

    @Size(max = 100)
    @JsonProperty("status")
    @Schema(examples = "PENDENTE")
    private String status;

    @Size(max = 400)
    @JsonProperty("comentario")
    @Schema(examples = "comentario")
    private String comentario;
}
