package com.example.testa.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Objects;

public class PokerCard implements Comparable<PokerCard> {

    @SerializedName("suit")
    String suit;

    @SerializedName("value")
    String value;

    public PokerCard(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokerCard)) return false;
        PokerCard pokerCard = (PokerCard) o;
        return getSuit().equals(pokerCard.getSuit()) && getValue().equals(pokerCard.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuit(), getValue());
    }

    @Override
    public String toString() {
        return "PokerCard{" +
                "suit='" + suit + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    @Override
    public int compareTo(PokerCard pokerCard) {
        int value1 = this.getValue().compareTo(pokerCard.getValue());
        if (value1 == 0) {
            int value2 = this.getSuit().compareTo(pokerCard.getSuit());
            return value2;
        }
        return value1;
    }
}
