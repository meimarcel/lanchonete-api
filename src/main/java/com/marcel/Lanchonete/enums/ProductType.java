package com.marcel.Lanchonete.enums;

public enum ProductType {
    BEBIDA("Bebida"),
    COMIDA("Comida"),
    SOBREMESA("Sobremesa");

    private String productType;

    private ProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return this.productType;
    }

    public static ProductType parser(String productType) {
        if(productType != null && !productType.isEmpty()) {
            for(ProductType p : ProductType.values()) {
                if(p.toString().toLowerCase().equals(productType.toLowerCase())) {
                    return p;
                }
            }
        }
        return null;
    }

}
