package com.urbano.autenticacion.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AutenticacionNotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(AutenticacionNotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException e){Map<String,String> err=new HashMap<>();e.getBindingResult().getFieldErrors().forEach(f->err.put(f.getField(),f.getDefaultMessage()));return ResponseEntity.badRequest().body(err);}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> general(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",e.getMessage()));}
}
