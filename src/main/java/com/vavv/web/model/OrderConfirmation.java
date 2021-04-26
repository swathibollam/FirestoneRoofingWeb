package com.vavv.web.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "OrderConfirmation")
@Table(name = "order_confirmation")
public class OrderConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "confirmed_at", nullable = false, columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmedAt;

    @Column(name = "prior_days", nullable = false)
    private int priorDays;

    @Column(name = "order_id")
    private String orderId;

    public OrderConfirmation(boolean confirmed, Date confirmedAt, int priorDays, String orderId) {
        this.confirmed = confirmed;
        this.confirmedAt = confirmedAt;
        this.priorDays = priorDays;
        this.orderId = orderId;
    }

    public OrderConfirmation() {
    }

    public long getId() {
        return id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public int getPriorDays() {
        return priorDays;
    }

    public void setPriorDays(int priorDays) {
        this.priorDays = priorDays;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderConfirmation)) return false;
        OrderConfirmation that = (OrderConfirmation) o;
        return id == that.id &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderId);
    }

    @Override
    public String toString() {
        return "OrderConfirmation{" +
                "id=" + id +
                ", confirmed=" + confirmed +
                ", confirmedAt=" + confirmedAt +
                ", priorDays=" + priorDays +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
