package me.tapumandal.ovirupa.domain.promotions;

import me.tapumandal.ovirupa.repository.Repository;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;

public interface PromotionRepository extends Repository<Promotion> {

    public Session getSession();
//    public Promotion create(Promotion promotion);
//    public Promotion update(Promotion promotion);
    public Promotion applyPromotion(String code);
    public int getAllEntityCount(Pageable pageable, String flag);

}
