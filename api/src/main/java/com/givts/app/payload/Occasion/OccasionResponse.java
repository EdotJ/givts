package com.givts.app.payload.Occasion;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OccasionResponse {

    private List<SingleOccasionResponse> occasions;

    @JsonProperty("count")
    public int getOccasionCount() {
        return occasions.size();
    }

    public List<SingleOccasionResponse> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<SingleOccasionResponse> occasions) {
        this.occasions = occasions;
    }
}
