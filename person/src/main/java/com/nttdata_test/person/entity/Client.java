package com.nttdata_test.person.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String clientId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Boolean status;

  @OneToOne
  @JoinColumn(name = "person_id", nullable = false)
  private Person person;
}
