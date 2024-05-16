package org.example.paypal.feign;

import org.example.paypal.dto.AddTransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
@FeignClient(name = "transaction", url = "http://localhost:8070/api")
public interface TranactionClient {
    @PostMapping("/v1/transactions/addTransaction")
    void addTranactionHistory(AddTransactionDto addTransactionDto);
}
