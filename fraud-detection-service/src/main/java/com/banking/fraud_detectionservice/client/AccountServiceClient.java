package com.banking.fraud_detectionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "${account.service.url}")
/**
 * You don't need to implement this interface manually.
 * Spring Cloud OpenFeign automatically generates an implementation at runtime.
 */
public interface AccountServiceClient {
    @GetMapping("/api/v1/accounts/{accountNumber}/balance")
    BigDecimal getBalance(@PathVariable String accountNumber);
}
