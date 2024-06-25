package vn.fpt.diamond_shop.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.fpt.diamond_shop.util.validator.RequestValidateAOPHandle;

import java.util.List;

@UtilityClass
public class BaseResponse {
    public static ResponseEntity<List<RequestValidateAOPHandle.InvalidProperties>> responseError(List<RequestValidateAOPHandle.InvalidProperties> errors) {
        return ResponseEntity.badRequest().body(errors);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> ok(T body, int page, int size, long total) {
        return new ResponseEntity<T>(body, page(page, size, total), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> bad(T body) {
        return ResponseEntity.badRequest().body(body);
    }

    private static HttpHeaders page(int page, int size, long total) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("page", String.valueOf(page));
        headers.add("size", String.valueOf(size));
        headers.add("total", String.valueOf(total));
        return headers;
    }
}
