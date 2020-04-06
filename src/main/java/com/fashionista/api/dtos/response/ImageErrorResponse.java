package com.fashionista.api.dtos.response;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageErrorResponse {
    private Data data;
    private boolean success;
    private int status;

    @Getter
    @ToString
    private class Data {
        private String error;
        private String request;
        private String method;
    }
}
