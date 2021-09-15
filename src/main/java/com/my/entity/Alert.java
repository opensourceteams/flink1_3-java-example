package com.my.entity;

import java.util.Objects;

public class Alert {

    private long id;
    private long timestamp;
    private double amount;

    public Alert() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Alert alert = (Alert)o;
            return this.id == alert.id;
        } else {
            return false;
        }
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

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }


    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                '}';
    }
}
