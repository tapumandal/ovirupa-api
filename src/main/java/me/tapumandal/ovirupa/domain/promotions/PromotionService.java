package me.tapumandal.ovirupa.domain.promotions;

import me.tapumandal.ovirupa.entity.UserPromo;
import me.tapumandal.ovirupa.service.Service;

public interface PromotionService extends Service<PromotionDto, Promotion> {
    public UserPromo applyPromotion(String code);
//    public int getAllEntityCount(Pageable pageable, ListFilter listFilter);
}
