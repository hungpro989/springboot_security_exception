package com.demo.security.persistance.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Table(name = "role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ERole name;


}
