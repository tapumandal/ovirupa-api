package me.tapumandal.ovirupa.domain.sms;

import me.tapumandal.ovirupa.service.Service;

import java.util.List;

public interface SMSService extends Service<SMSDto, SMS> {
    public List<SMS> gethighlight();
    public String sendOTP(String phoneNumber);
}
