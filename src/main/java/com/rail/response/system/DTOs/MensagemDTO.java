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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemDTO {

    @Schema(examples = "1")
    private Integer id;

    @Schema(examples = "Temperatura acima do limite em setor 5.")
    @Size(max = 4000)
    private String conteudo;

    @Schema(examples = "usuario@example.com")
    @Size(max = 20)
    private String contato;

    @Schema(examples = "2024-05-20")
    @JsonProperty("data_criacao")
    private LocalDate dataCriacao;

    @Schema(examples = "2024-05-22")
    @JsonProperty("data_atualizacao")
    private LocalDate dataAtualizacao;

    @Schema(examples = "1")
    @JsonProperty("id_usuario")
    private Integer idUsuario;
}
