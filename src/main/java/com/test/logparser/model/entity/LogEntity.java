package com.test.logparser.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogEntity {

    @Id
    @Column
    private String event_id;

    @Column
    private Integer event_duration;

    @Column
    private String type;

    @Column
    private String host;

    @Column
    private Boolean alert;

}
