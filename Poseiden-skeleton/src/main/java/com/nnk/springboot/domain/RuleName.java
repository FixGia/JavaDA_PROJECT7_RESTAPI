package com.nnk.springboot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name ="Rule_name")
public class RuleName {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  Integer id;
  private  String name;
  private String description;
  private  String json;
  private String template;
  private  String sqlStr;
  private  String sqlPart;

}
