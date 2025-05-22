package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoNotificacaoDTO {

    @Schema(examples = "1")
    private Integer id;

    @Schema(examples = "ALERTA")
    @JsonProperty("tp")
    @Size(max = 50)
    private String tp;

    @Schema(examples = "2024-05-01")
    @JsonProperty("data_criacao")
    private LocalDate dataCriacao;

    @Schema(examples = "2024-05-22")
    @JsonProperty("data_atualizacao")
    private LocalDate dataAtualizacao;

    @Schema(examples = "1001")
    @JsonProperty("id_notificacao")
    private Integer idNotificacao;}
