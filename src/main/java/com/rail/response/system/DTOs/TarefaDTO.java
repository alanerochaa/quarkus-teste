package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarefaDTO {
    @Schema(examples = "101")
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @Size(max = 200)
    @Schema(examples = "Alerta de temperatura")
    @JsonProperty("titulo")
    private String titulo;

    @Size(max = 4000)
    @Schema(examples = "Temperatura acima do limite permitido no setor 3.")
    @JsonProperty("descricao")
    private String descricao;

    @NotNull
    @Size(max = 50)
    @Schema(examples = "PENDENTE")
    @JsonProperty("status")
    private String status;

    @Schema(examples = "2024-05-01")
    @JsonProperty("data_criacao")
    private LocalDate dataCriacao;

    @Schema(examples = "2024-05-22")
    @JsonProperty("data_atualizacao")
    private LocalDate dataAtualizacao;

    @NotNull
    @Schema(examples = "1")
    @JsonProperty("id_usuario")
    private Integer idUsuario;
}
