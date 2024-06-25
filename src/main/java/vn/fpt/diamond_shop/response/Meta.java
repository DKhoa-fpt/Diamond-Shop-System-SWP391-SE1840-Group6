package vn.fpt.diamond_shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {
    private String requestId;

    private int code;

    private String message;

    private String httpCode;

    private Integer limit;
    private Integer offset;
    private Integer total;

    public Meta(String requestId, int code, String message, String httpCode) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

    public Meta(String requestId, int code, String message, String httpCode, Integer limit, Integer offset, Integer total) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }
}