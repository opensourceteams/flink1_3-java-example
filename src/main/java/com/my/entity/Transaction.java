package com.my.entity;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/** A simple transaction. */
@SuppressWarnings("unused")
public final class Transaction {

    private long id;
    private long accountId;

    private long timestamp;

    private double amount;

    public Transaction() {}

    public Transaction(long id, long accountId, long timestamp, double amount) {
        this.id = id;
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Transaction(long accountId, long timestamp, double amount) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return accountId == that.accountId
                && timestamp == that.timestamp
                && Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, timestamp, amount);
    }

    @Override
    public String toString() {
        Date date = new Date(timestamp);
        String timestampString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", timestamp=" + timestamp +
                ", timestampString=" + timestampString +
                ", amount=" + amount +
                '}';
    }
}
