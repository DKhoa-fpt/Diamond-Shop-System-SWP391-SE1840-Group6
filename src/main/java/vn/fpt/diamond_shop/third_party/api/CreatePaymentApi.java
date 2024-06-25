package vn.fpt.diamond_shop.third_party.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.stereotype.Component;
import vn.fpt.diamond_shop.constants.PayTypeEnum;
import vn.fpt.diamond_shop.third_party.payload.CreatePaymentRequest;
import vn.fpt.diamond_shop.third_party.payload.CreatePaymentResponse;

@Component
public class CreatePaymentApi {

    private final String url = "http://178.128.111.191:8081/v1/payment/create";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public CreatePaymentResponse create(String requestId, String orderId, String orderDetail, long amount, PayTypeEnum payTypeEnum) {
        try {
            String request = objectMapper.writeValueAsString(new CreatePaymentRequest(requestId, orderId, amount, orderDetail, payTypeEnum.name()));
            HttpResponse<JsonNode> response = Unirest
                    .post(url)
                    .header("Content-Type", "application/json")
                    .body(request)
                    .asJson();
            if (response.getStatus() != 200) {
                throw new RuntimeException("Create payment failed");
            }
            return objectMapper.readValue(response.getBody().toString(), CreatePaymentResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
