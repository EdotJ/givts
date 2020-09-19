package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GifteeResponse {

    private List<SingleGifteeResponse> giftees;

    @JsonProperty("count")
    public int getGifteeCount() {
        return giftees.size();
    }

    public List<SingleGifteeResponse> getGiftees() {
        return giftees;
    }

    public void setGiftees(List<SingleGifteeResponse> giftees) {
        this.giftees = giftees;
    }
}
