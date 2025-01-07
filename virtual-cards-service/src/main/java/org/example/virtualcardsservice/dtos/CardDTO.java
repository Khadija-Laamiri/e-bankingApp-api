package org.example.virtualcardsservice.dtos;

import com.stripe.model.issuing.Card;

public class CardDTO {
    private String id;
    private String status;
    private Long expMonth;
    private Long expYear;
    private String brand;
    private String number;
    private String cvc;

    public CardDTO(String id, String status, Long expMonth, Long expYear, String brand, String number, String cvc) {
        this.id = id;
        this.status = status;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.brand = brand;
        this.number = number;
        this.cvc = cvc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Long expMonth) {
        this.expMonth = expMonth;
    }

    public Long getExpYear() {
        return expYear;
    }

    public void setExpYear(Long expYear) {
        this.expYear = expYear;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    static public CardDTO convertToDTO(Card card) {
        return new CardDTO(card.getId(), card.getStatus(), card.getExpMonth(), card.getExpYear(), card.getBrand(), card.getNumber(), card.getCvc());
    }


}
