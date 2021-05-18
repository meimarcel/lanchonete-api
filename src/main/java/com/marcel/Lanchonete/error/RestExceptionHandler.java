package com.marcel.Lanchonete.error;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.UnexpectedTypeException;

import com.marcel.Lanchonete.enums.DetailType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ResponseBody
    @Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Input Parse Error")
                .message("Verifique se o conteúdo está correto")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
	}

    @ResponseBody
    @Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Internal Error")
                .message("Houve um erro inesperado, tente novamente mais tarde.")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> fieldsValidations = ex.getBindingResult().getFieldErrors()
                .stream().map((object) -> {return object.getField()+": "+object.getDefaultMessage();}).collect(Collectors.toList());
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Fields Validation Error")
                .message(String.join("\n",fieldsValidations))
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
	}
	
	@ResponseBody
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> fieldsValidations = ex.getBindingResult().getFieldErrors()
                .stream().map((object) -> {return object.getField()+": "+object.getDefaultMessage();}).collect(Collectors.toList());
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Fields Validation Error")
                .message(String.join("\n",fieldsValidations))
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
	}

	@ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Bad Credentials")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
				.timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
	}

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("User Already Exist")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

	@ResponseBody
    @ExceptionHandler(UserDoesNotExistException.class)
    protected ResponseEntity<?> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("User Does Not Exist")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(PasswordNotMatchingException.class)
    protected ResponseEntity<?> handlePasswordNotMatchingException(PasswordNotMatchingException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Passwords Not Matching")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(InfoException.class)
    protected ResponseEntity<?> handleInfoException(InfoException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Information")
                .message(ex.getMessage())
                .type(ex.getType())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("User Not Found")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

	@ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Invalid token")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Internal Error")
                .message("Houve um erro inesperado, tente novamente mais tarde.")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(UnexpectedTypeException.class)
    protected ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Internal Error")
                .message("Houve um erro inesperado, tente novamente mais tarde.")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Not Enough Variable")
                .message("Número de variáveis insuficiente na request.")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Resource Not Found")
                .message(ex.getMessage())
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<?> handleResourceNotFoundException(SQLException ex) {
        MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Internal Error")
                .message("Houve um erro inesperado, tente novamente mais tarde.")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }
}
