package com.marcel.Lanchonete.enums;

public enum OrderStatus {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CANCELADO("Cancelado"),
    FINALIZADO("Finalizado");

    private String orderStatus;

    private OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public String getOrderStatus() {
        return this.orderStatus;
    }

    public static OrderStatus parser(String orderStatus) {
        if(orderStatus != null && !orderStatus.isEmpty()) {
            for(OrderStatus o : OrderStatus.values()) {
                if(o.toString().toLowerCase().equals(orderStatus.toLowerCase())) {
                    return o;
                }
            }
        }
        return null;
    }

}
