package com.nttdata_test.person.entity;

import com.nttdata_test.person.entity.dto.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Gender gender;

  private Integer age;

  @Column(unique = true, length = 10)
  private String identification;

  private String address;

  private String phoneNumber;
}
