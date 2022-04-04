package me.tapumandal.ovirupa.domain.payment_gateway;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.domain.cart.Cart;
import me.tapumandal.ovirupa.util.ApplicationPreferences;

import java.util.Map;

/**
 * This class handles the Response parameters redirected from payment success page.
 * Validates those parameters fetched from payment page response and returns true for successful transaction
 * and false otherwise.
 */
public class TransactionResponseValidator {
    /**
     *
     * @param request
     * @return
     * @throws Exception
     * Send Received params from your success resoponse (POST ) in this Map</>
     */
    public boolean receiveSuccessResponse(Map<String, String> request) throws Exception {

        String trxId = request.get("tran_id");
        /**
         *Get your AMOUNT and Currency FROM DB to initiate this Transaction
         */

        String cartTmp = ApplicationPreferences.getCart();
        Cart cart = new Gson().fromJson(cartTmp, Cart.class);

        String amount = cart.getTotalPayable()+"";
        String currency = "BDT";
        // Set your store Id and store password and define TestMode
        SSLCommerz sslcz = new SSLCommerz("timtomcombdlive", "611CD5214E62E20713", false);

        /**
         * If following order validation returns true, then process transaction as success.
         * if this following validation returns false , then query status if failed of canceled.
         *      Check request.get("status") for this purpose
         */
        return sslcz.orderValidate(trxId, amount, currency, request);

    }
}
