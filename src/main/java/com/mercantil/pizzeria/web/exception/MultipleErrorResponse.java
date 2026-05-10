package com.mercantil.pizzeria.web.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Builder
public class MultipleErrorResponse {

    private final List<ErrorResponse> errores;

}
