package com.givts.app.payload.Gift;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GiftResponse {

    private List<SingleGiftResponse> gifts;

    @JsonProperty("count")
    public int getGiftCount() {
        return gifts.size();
    }

    public List<SingleGiftResponse> getGifts() {
        return gifts;
    }

    public void setGifts(List<SingleGiftResponse> gifts) {
        this.gifts = gifts;
    }
}
