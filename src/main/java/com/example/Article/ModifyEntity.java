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
    private String Value;

    public void update(String Value) {
        this.Value = Value;
    }
    public ModifyEntity(Long id, String Value) {
        this.id = id;
        this.Value = Value;
    }

    @Override
    public String toString() {
        return "ModifyEntity{" +
                ", id='" + id + '\'' +
                ", Value=" + Value + '\'' +
                '}';
    }
}
