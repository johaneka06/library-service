package com.example.library.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@ToString
public class Test {
    @Id
    private String id;
    private String name;
    private String desc;

    public Test() { }

    public Test(String name, String desc) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.desc = desc;
    }
}
