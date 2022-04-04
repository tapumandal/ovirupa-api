package me.tapumandal.ovirupa.domain.sms;

import org.hibernate.Session;

public interface SMSRepository {

    public Session getSession();
    public SMS create(SMS SMS);
    public SMS update(SMS SMS);
    public SMS gethighlight();
}
