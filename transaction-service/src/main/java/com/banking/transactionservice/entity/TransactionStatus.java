package com.banking.transactionservice.entity;
/**
 * Transaction Lifecycle Flow:
 * PENDING -> PROCESSING -> COMPLETED(clean transaction)
 *                       -> PENDING VERIFICATION(SUSPICIOUS DETECTED)
 *                          -> COMPLETED
 *                          -> FLAGGED
 *                       -> FAILED
 *                       -> FLAGGED
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    PENDING_VERIFICATION,
    COMPLETED,
    FAILED,
    FLAGGED
}
