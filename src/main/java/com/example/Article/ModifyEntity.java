package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity
public class ModifyEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String value;

    public ModifyEntity(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ModifyEntity{" +
                ", id='" + id + '\'' +
                ", value=" + value + '\'' +
                '}';
    }
}
