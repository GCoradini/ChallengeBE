package com.mercantil.pizzeria.web.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ErrorResponse {

    private final String error;
}
