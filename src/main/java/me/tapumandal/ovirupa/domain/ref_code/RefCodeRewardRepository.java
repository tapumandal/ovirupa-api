package me.tapumandal.ovirupa.domain.ref_code;

import me.tapumandal.ovirupa.repository.Repository;

public interface RefCodeRewardRepository extends Repository<RefCodeReward> {

    public RefCodeReward getRefCodeRewardFirstTime(int refCodeRewardId);
    public RefCodeReward getRefCodeByCode(String code);
}
