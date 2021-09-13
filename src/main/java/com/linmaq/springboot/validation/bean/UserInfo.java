package com.linmaq.springboot.validation.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Class : UserInfo
 * @author: marin
 * @date : 21:30 2021/9/13
 */
@Data
public class UserInfo {
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
