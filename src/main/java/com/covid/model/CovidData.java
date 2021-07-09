package com.covid.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "COVID")
public class CovidData {
    @Id
    @GeneratedValue
    Integer id;
    @Column
    private String city;
    @Column
    private String province;
    @Column
    private String country;
    @Column
    private String lastUpdate;
    @Column
    private String keyId;
    @Column
    private String deaths;
    @Column
    private String recovered;
    @Column
    private Date createdDate;
}
