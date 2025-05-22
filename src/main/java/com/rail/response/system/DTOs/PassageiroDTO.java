package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class PassageiroDTO {

    @Schema(examples = "1")
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Schema(examples = "Empresa XYZ")
    private String nome;

    @NotNull
    @Size(max = 50)
    @Schema(examples = "12.345.678/1111-90")
    private String documento;

    @Size(max = 4000)
    @Schema(examples = "Responsável pela manutenção dos sensores da planta industrial.")
    private String descricao;

    @Size(max = 100)
    @Schema(examples = "João da Silva")
    private String responsavel;

    @Size(max = 20, message = "O contato deve ter no máximo 20 caracteres")
    @Schema(examples = "joao.silva@empresa.com")
    private String contato;
    
    @JsonProperty("data_criacao")
    @Schema(examples = "2024-05-20")
    private LocalDate dataCriacao;

    @JsonProperty("data_atualizacao")
    @Schema(examples = "2024-05-22")
    private LocalDate dataAtualizacao;
}
