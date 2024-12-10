package com.volonteers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;


@Getter
@Setter
public class AuthRequest
{
    private String token;

    private Collection<?> roles;
}
