package com.linmaq.springboot.valid;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Class : Payment
 * @author: marin
 * @date : 21:37 2021/9/12
 */
@Getter
@Setter
@ToString
public class Payment {
    @NotNull
    private String type;
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
    private String date;
    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String account;
    @NotNull
    @Length(max = 20, min = 2)
    private String username;
    @AssertTrue
    private boolean isSelf;
}
