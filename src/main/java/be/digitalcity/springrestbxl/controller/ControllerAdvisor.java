package be.digitalcity.springrestbxl.controller;
import be.digitalcity.springrestbxl.exceptions.ElementNotFoundException;
import be.digitalcity.springrestbxl.exceptions.FormValidationException;
import be.digitalcity.springrestbxl.exceptions.InvalidReferenceException;
import be.digitalcity.springrestbxl.exceptions.ReferencedSuppressionException;
import be.digitalcity.springrestbxl.model.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.function.Function;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(ElementNotFoundException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateErrorResponse(HttpStatus.NOT_FOUND, ex, req));
    }

    @ExceptionHandler(ReferencedSuppressionException.class)
    public ResponseEntity<ErrorDTO> handleException( ReferencedSuppressionException ex, HttpServletRequest req ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( generateErrorResponse(HttpStatus.BAD_REQUEST, ex, req) );
    }

    @ExceptionHandler(InvalidReferenceException.class)
    public ResponseEntity<ErrorDTO> handleException(InvalidReferenceException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        generateErrorResponse(
                                HttpStatus.UNPROCESSABLE_ENTITY, ex, req,
                                error -> error.addInfo("invalidReferences", ex.getNotFound())
                        )
                );
    }

    @ExceptionHandler(FormValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(FormValidationException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        generateErrorResponse(
                                HttpStatus.BAD_REQUEST, ex, req,
                                error -> error.addInfo("errors", ex.getMessages())
                        )
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        generateErrorResponse(HttpStatus.BAD_REQUEST, ex,
                                errorDTO -> {
                                    for (ObjectError globalError : ex.getGlobalErrors()) {
                                        errorDTO = errorDTO.addInfo("global", globalError.getDefaultMessage());
                                    }
                                    for (FieldError fieldError : ex.getFieldErrors()) {
                                        errorDTO = errorDTO.addInfo(fieldError.getField(), fieldError.getDefaultMessage());
                                    }
                                    return errorDTO;
                                }
                        )
                );
    }

    private ErrorDTO generateErrorResponse(HttpStatus status, Exception ex, Function<ErrorDTO, ErrorDTO> andAdd){
        ErrorDTO errorDTO = generateErrorResponse(status, ex);
        return andAdd.apply(errorDTO);
    }

    private ErrorDTO generateErrorResponse(HttpStatus status, Exception ex){
        return ErrorDTO.builder()
                .message(ex.getMessage())
                .receivedAt( LocalDateTime.now() )
                .status( status.value() )
                .build();
    }

    private ErrorDTO generateErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request){
        return ErrorDTO.builder()
                .message(ex.getMessage())
                .receivedAt( LocalDateTime.now() )
                .status( status.value() )
                .method( HttpMethod.resolve(request.getMethod()) )
                .path( request.getRequestURL().toString() )
                .build();
    }

    private ErrorDTO generateErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request, Function<ErrorDTO, ErrorDTO> andAdd){
        ErrorDTO error = generateErrorResponse(status, ex, request);
        return andAdd.apply(error);
    }


}
