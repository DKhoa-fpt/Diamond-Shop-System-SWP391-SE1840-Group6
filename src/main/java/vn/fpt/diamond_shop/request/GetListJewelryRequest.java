package vn.fpt.diamond_shop.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetListJewelryRequest extends BaseRequest{
    private Long jewelryTypeId;
    private Boolean budget1;
    private Boolean budget2;
    private Boolean budget3;
    private String title;
    private Integer limit;
    private Integer offset;
    private String sort;
}
