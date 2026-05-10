package com.mercantil.pizzeria.web.exception;

import com.mercantil.pizzeria.infra.exception.ProductoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {

    private static final String DESCRIPCION_NO_ENCONTRADO = "Producto no encontrado";

    @ExceptionHandler(ProductoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(RuntimeException e) {
        log.error("Error: {}", e.getMessage());
        return new ErrorResponse(DESCRIPCION_NO_ENCONTRADO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MultipleErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        List<ErrorResponse> listaErrores = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    log.warn(
                            "Error de validación: {} - {}",
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    );

                    return ErrorResponse.builder()
                            .error(error.getDefaultMessage())
                            .build();
                })
                .toList();

        return new MultipleErrorResponse(listaErrores);
    }

}
