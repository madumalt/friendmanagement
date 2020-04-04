package com.sps.friendmanagement.handler;

import com.sps.friendmanagement.response.ErrorResponse;
import com.sps.friendmanagement.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FriendManagementExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeErros() {

        return new ResponseEntity<>(
                new ErrorResponse(ErrorMessages.UNEXPECTED_SERVER_SIDE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
