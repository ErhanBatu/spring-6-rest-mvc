package guru.springframework.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//ControllerAdvice: it will picked up globally you can use it everywhere
//you don't need this class anymore because i put @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "value not found")
//in the NotFoundException class
//@ControllerAdvice
//public class ExceptionController {
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException(){
//
//        System.out.println("in exception handler");
//
//        return ResponseEntity.notFound().build();
//    }
//
//}
