package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "example")
public class SomeEntity {
    @Id
    private long id;
}
