package vn.fpt.diamond_shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import vn.fpt.diamond_shop.response.BaseResponse;
import vn.fpt.diamond_shop.response.Meta;
import vn.fpt.diamond_shop.util.UUIDUtil;

public class   BaseController {
    public ResponseEntity<Object> ok(Object payload ) {
        String requestId = UUIDUtil.generateUUID();
        BaseResponse response = new BaseResponse(new Meta(requestId, 200, "success", HttpStatus.OK.toString()),payload);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> ok(Object payload , String requestId) {
        BaseResponse response = new BaseResponse(new Meta(requestId, 200, "success", HttpStatus.OK.toString()),payload);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> err(String message, String requestId){
        BaseResponse response = new BaseResponse(new Meta(requestId, 400, "bad request", HttpStatus.BAD_REQUEST.toString()),message);
        return ResponseEntity.badRequest().body(response);
    }

    public ResponseEntity<Object> internalErr(String message, Throwable throwable, String requestId){
        BaseResponse response = new BaseResponse(new Meta(requestId, 500, "internal server error", HttpStatus.INTERNAL_SERVER_ERROR.toString()),message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getClass().getName() + ": " + throwable.getMessage());
    }

    public ResponseEntity<?> noContent(){
        return ResponseEntity.noContent().build();
    }
}
