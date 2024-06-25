package vn.fpt.diamond_shop.service;

import vn.fpt.diamond_shop.constants.DiamondClarityEnum;
import vn.fpt.diamond_shop.constants.DiamondColorEnum;
import vn.fpt.diamond_shop.model.Diamond;
import vn.fpt.diamond_shop.request.AddDiamondRequest;
import vn.fpt.diamond_shop.response.ListDiamondReponse;
import vn.fpt.diamond_shop.response.GetDetailDiamondResponse;

import java.util.List;

public interface DiamondService {
    void addDiamond(AddDiamondRequest addDiamondRequest);

    List<Diamond> listDiamonds();

    GetDetailDiamondResponse getDetailDiamondResponse(long id);

    int getDiamondPrice(double weight, DiamondClarityEnum clarityEnum, DiamondColorEnum diamondColorEnum);
}
