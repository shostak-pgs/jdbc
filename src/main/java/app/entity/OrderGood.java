package app.entity;

import java.util.Objects;

public class OrderGood {
    private long id;
    private long orderId;
    private long goodId;

    public OrderGood(long id, long orderId, long goodId) {
        this.id = id;
        this.orderId = orderId;
        this.goodId = goodId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getGoodId() {
        return goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGood orderGood = (OrderGood) o;
        return id == orderGood.id &&
                orderId == orderGood.orderId &&
                goodId == orderGood.goodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, goodId);
    }

    @Override
    public String toString() {
        return "OrderGood{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodId=" + goodId +
                '}';
    }
}
