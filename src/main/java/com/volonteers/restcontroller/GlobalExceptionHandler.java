package com.volonteers.restcontroller;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, Object>> handleSqlError(SQLException ex) {
        String sqlState = ex.getSQLState();
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Database Error");
        response.put("message", ex.getMessage());

        if (sqlState != null && sqlState.startsWith("08")) {
            // База данных недоступна
            response.put("details", " База данных недоступна.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } else if (sqlState != null && (sqlState.startsWith("42") || sqlState.equals("HY000"))) {
            // База данных повреждена
            response.put("details", "База данных повреждена");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            // Общая ошибка SQL
            response.put("details", "База данных повреждена.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Database Access Error");
        response.put("message", ex.getMessage());
        response.put("details", "Проверьте настройки подключения в application.properties");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



}
