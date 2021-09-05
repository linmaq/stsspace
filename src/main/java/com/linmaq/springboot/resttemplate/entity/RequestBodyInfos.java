package com.linmaq.springboot.resttemplate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Class : RequestBodyInfos
 * @author: marin
 * @date : 20:53 2021/9/5
 */
@Getter
@Setter
@ToString
public class RequestBodyInfos {

    @NotNull
    @Length(max = 20, min = 1)
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;
    @NotNull
    @JsonProperty
    private int age;

    @JsonFormat(pattern = "YYYY-MM-DD")
    @NotNull
    private Date date;
}
