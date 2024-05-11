package org.example.paypal.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.example.paypal.exeption.AppExeption;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorContent = "";
        try {
            errorContent = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(errorContent, Map.class);
            errorContent = map.get("message");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AppExeption(errorContent, HttpStatus.valueOf(response.status()));
    }
}