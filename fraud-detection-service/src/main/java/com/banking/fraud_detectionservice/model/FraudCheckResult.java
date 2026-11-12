package com.banking.fraud_detectionservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FraudCheckResult {
    public FraudCheckResult(boolean fraud, String reason) {
        this.fraud = fraud;
        this.reason = reason;
    }
    private boolean fraud;
    private String reason;

    public boolean isFraud() {
        return fraud;
    }

    public void setFraud(boolean fraud) {
        this.fraud = fraud;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
