package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContatoDTO {

    @Schema(examples = "1")
    @JsonProperty("id")
    private Long id;

    @Schema(examples = "joao@email.com")
    @JsonProperty("valor_contato")
    @Size(max = 100)
    private String valorContato;

    @Schema(examples = "EMAIL")
    @JsonProperty("tipo_contato")
    @Size(max = 50)
    private String tipoContato;

    @Schema(examples = "2024-05-20T15:30:00Z")
    @JsonProperty("data_criacao")
    private Date dataCriacao;

    @Schema(examples = "2024-05-21T10:15:00Z")
    @JsonProperty("data_atualizacao")
    private Date dataAtualizacao;

    @Schema(examples = "1")
    @JsonProperty("id_usuario")
    private Long idUsuario;
}
