package com.nnk.springboot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;
    private String account;
    private String type;
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

}
