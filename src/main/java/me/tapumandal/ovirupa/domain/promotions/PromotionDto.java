package me.tapumandal.ovirupa.domain.promotions;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PromotionDto {

    protected int id;

    protected String code;

    protected String type; //RefCode/PromoCode

    protected String startDate;

    protected String expireDate;

    protected int credit = 0;

    protected int minimumPurchase = 0;

    protected String area; //All or Specific

    private boolean isActive = true;

    protected int numberOfApply = 0;

    protected String promoTitle = "";

    protected String promoMessage = "";

    public PromotionDto() {}

}
