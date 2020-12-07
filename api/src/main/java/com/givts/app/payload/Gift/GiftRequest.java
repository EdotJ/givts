package com.givts.app.payload.Gift;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class GiftRequest {

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    private String description;

    @NotNull(message = "{gift.price.notNull}")
    @PositiveOrZero(message = "{gift.price.notNegative}")
    private BigDecimal price;

    public GiftRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
