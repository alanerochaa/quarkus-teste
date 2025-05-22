package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstacaoDTO {

    @Schema(examples = "1")
    @JsonProperty("id")
    private Integer id;

    @Schema(examples = "João da Silva")
    @JsonProperty("nome")
    @Size(max = 100)
    private String nome;

    @Schema(examples = "Rua das Flores, 11111 - São Paulo, SP")
    @JsonProperty("endereco")
    @Size(max = 255)
    private String endereco;

    @Schema(examples = "(11) 11111-1111")
    @JsonProperty("telefone")
    @Size(max = 20)
    private String telefone;

    @Schema(examples = "2023-01-01T08:00:00Z")
    @JsonProperty("data_inicio")
    private Date dataInicio;

    @Schema(examples = "B-15")
    @JsonProperty("carro")
    @Size(max = 20)
    private String carro;

    @Schema(examples = "2024-05-20T10:00:00Z")
    @JsonProperty("data_criacao")
    private Date dataCriacao;

    @Schema(examples = "2024-05-21T15:30:00Z")
    @JsonProperty("data_atualizacao")
    private Date dataAtualizacao;
}
