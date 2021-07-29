package org.cmsc495.bpo.controllers.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity<Object> {

    private ApiResponseBody body;

    private ApiResponse() {
        super(HttpStatus.OK);
        this.body = new ApiResponseBody();
    }

    @Override
    public ApiResponseBody getBody() {
        return this.body;
    }

    public static ApiResponse ok(Object message) {
        return new ApiResponse().withStatusCode(200).withMessage(message);
    }

    public static ApiResponse error(int statusCode, Object message) {
        return new ApiResponse().withStatusCode(statusCode).withMessage(message);
    }

    public ApiResponse withStatusCode(int statusCode) {
        this.body.setStatusCode(statusCode);
        return this;
    }

    public ApiResponse withMessage(Object message) {
        this.body.setMessage(message);
        return this;
    }

    public static class ApiResponseBody {
        private int statusCode;
        private Object message;

        public ApiResponseBody() {
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public Object getMessage() {
            return this.message;
        }

        /**
         * Attempts to parse the contained message as the given clazz.
         * 
         * @param <E>
         * @param clazz
         * @return
         * @throws JsonProcessingException
         */
        public <E> E getMessageAs(Class<E> clazz) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(mapper.writeValueAsString(this.message), clazz);
        }
    }
}
