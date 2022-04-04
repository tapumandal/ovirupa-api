package me.tapumandal.ovirupa.domain.fcm_registration_token;

import me.tapumandal.ovirupa.repository.Repository;

import java.util.List;

public interface FCMRegistrationTokenRepository  extends Repository<FCMRegistrationToken> {
    public List<FCMRegistrationToken> getAll();
}
