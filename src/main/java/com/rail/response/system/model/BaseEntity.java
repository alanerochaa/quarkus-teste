package com.rail.response.system.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity extends PanacheEntityBase {

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Column(name = "dt_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        if (this.dataCriacao == null) this.dataCriacao = now;
        this.dataAtualizacao = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDate.now();
    }
}
