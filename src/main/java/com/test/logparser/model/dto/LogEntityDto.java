package com.test.logparser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntityDto {

    private String id;

    private String state;

    private String type;

    private String host;

    private Long timestamp;

    public String toString() {
        return "id : "+id+", state : "+state+", type : "+type+", host : "+host+", timestamp : "+timestamp;
    }
}