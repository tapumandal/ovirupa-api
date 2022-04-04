package me.tapumandal.ovirupa.domain.payment_gateway.Utility;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.domain.cart.Cart;
import me.tapumandal.ovirupa.domain.payment_gateway.parametermappings.SSLCommerzValidatorResponse;
import me.tapumandal.ovirupa.repository.RandomJSONRepository;
import me.tapumandal.ovirupa.util.ApplicationPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {


    @Autowired
    ApplicationPreferences applicationPreferences;

    @Value("${base.path.front}")
    String userBucketPath;

    @Value("${base.path.api}")
    String applicationApiBasePath;

    @Autowired
    static RandomJSONRepository randomJSONRepository;

    public static String getParamsString(Map<String, String> params, boolean urlEncode) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (urlEncode)
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            else
                result.append(entry.getKey());

            result.append("=");
            if (urlEncode)
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            else
                result.append(entry.getValue());
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static Map<String, String> constructRequestParameters(String json) {


//        RandomJSON randomJSON = new RandomJSON();
//        randomJSON = randomJSONRepository.findById(Integer.parseInt(ApplicationPreferences.getCart())).get();
//
//        String cartTmp = ApplicationPreferences.getCart();

        System.out.println("constructRequestParameters:");
        System.out.println(json);

        Cart cart = new Gson().fromJson(json, Cart.class);


        // CREATING LIST OF POST DATA
        String baseUrl = "https://api.ovirupa.com.bd/api/v1/";//Request.Url.Scheme + "://" + Request.Url.Authority + Request.ApplicationPath.TrimEnd('/') + "/";
//        String baseUrl = "http://localhost:8081/api/v1/";//Request.Url.Scheme + "://" + Request.Url.Authority + Request.ApplicationPath.TrimEnd('/') + "/";
//        String baseUrl = applicationApiBasePath+"";//Request.Url.Scheme + "://" + Request.Url.Authority + Request.ApplicationPath.TrimEnd('/') + "/";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("total_amount", cart.getTotalPayable()+"");
        postData.put("tran_id", "OVIRUPA"+cart.getInvoiceNo());
        postData.put("success_url", baseUrl + "payment/confirmation");
        postData.put("fail_url", baseUrl + "payment/confirmation");
        postData.put("cancel_url", baseUrl + "payment/confirmation");
//        postData.put("fail_url", "https://sandbox.sslcommerz.com/developer/fail.php");
//        postData.put("cancel_url", "https://sandbox.sslcommerz.com/developer/cancel.php");
        postData.put("version", "3.00");
        postData.put("cus_name", cart.getName());
        postData.put("cus_email", cart.getMobileNo());
        postData.put("cus_add1", cart.getAddress());
        postData.put("cus_add2", "Address Line Tw");
        postData.put("cus_city", "City");
        postData.put("cus_state", "State Nam");
        postData.put("cus_postcode", "Post Cod");
        postData.put("cus_country", "Bangladesh");
        postData.put("cus_phone", cart.getMobileNo());
        postData.put("cus_fax", "");
        postData.put("ship_name", "Ovirupa");
        postData.put("ship_add1", "Address Line On");
        postData.put("ship_add2", "Address Line Tw");
        postData.put("ship_city", "City Nam");
        postData.put("ship_state", "State Nam");
        postData.put("ship_postcode", "Post Cod");
        postData.put("ship_country", "Countr");
        postData.put("value_a", "ref00");
        postData.put("value_b", "ref00");
        postData.put("value_c", "ref00");
        postData.put("value_d", "ref00");
        postData.put("store_id", "timtomcombdlive");
        return postData;
    }

    public Map<String, String> successResponseParamBuilder(SSLCommerzValidatorResponse response){
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("status", response.getStatus());
        postData.put("tran_date", response.getTran_date());
        postData.put("tran_id", response.getTran_id());
        postData.put("val_id", response.getVal_id());
        postData.put("amount", response.getAmount());
        postData.put("store_amount", response.getStore_amount());
        postData.put("currency", response.getCurrency());
        postData.put("bank_tran_id", response.getBank_tran_id());
        postData.put("card_type", response.getCard_type());
        postData.put("card_no", response.getCard_no());
        postData.put("card_issuer", response.getCard_issuer());
        postData.put("card_brand", response.getCard_brand());
        postData.put("card_issuer_country", response.getCard_issuer_country());
        postData.put("card_issuer_country_code", response.getCard_issuer_country_code());
        postData.put("currency_type", response.getCurrency_type());
        postData.put("currency_amount", response.getCurrency_amount());
        postData.put("currency_rate", response.getCurrency_rate());
        postData.put("base_fair", response.getBase_fair());
        postData.put("value_a", response.getValue_a());
        postData.put("value_b", response.getValue_b());
        postData.put("value_c", response.getValue_c());
        postData.put("value_d", response.getValue_d());
        postData.put("emi_instalment", response.getEmi_instalment());
        postData.put("emi_amount", response.getEmi_amount());
        postData.put("emi_description", response.getEmi_description());
        postData.put("emi_issuer", response.getEmi_issuer());
        postData.put("account_details", response.getAccount_details());
        postData.put("risk_title", response.getRisk_title());
        postData.put("risk_level", response.getRisk_level());
        postData.put("APIConnect", response.getAPIConnect());
        postData.put("validated_on", response.getValidated_on());
        postData.put("gw_version", response.getGw_version());
        postData.put("token_key", response.getToken_key());

        postData.put("verify_sign", response.getVerify_sign());
        postData.put("verify_key", response.getVerify_key());
        postData.put("store_id", response.getStore_id());
        postData.put("cus_fax", response.getCus_fax());

        return postData;
    }
}
