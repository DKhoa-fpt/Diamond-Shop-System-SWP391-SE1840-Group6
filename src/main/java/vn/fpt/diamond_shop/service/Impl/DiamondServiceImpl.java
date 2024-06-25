package vn.fpt.diamond_shop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpt.diamond_shop.constants.DiamondClarityEnum;
import vn.fpt.diamond_shop.constants.DiamondColorEnum;
import vn.fpt.diamond_shop.model.Diamond;
import vn.fpt.diamond_shop.repository.RapaportReportRepository;
import vn.fpt.diamond_shop.request.AddDiamondRequest;
import vn.fpt.diamond_shop.response.ListDiamondReponse;
import vn.fpt.diamond_shop.repository.DiamondRepository;
import vn.fpt.diamond_shop.response.GetDetailDiamondResponse;
import vn.fpt.diamond_shop.service.DiamondService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DiamondServiceImpl implements DiamondService {
    @Autowired
    private DiamondRepository diamondRepo;

    @Autowired
    private RapaportReportRepository rapaportReportRepository;

    private final int BASE_PRICE_USD = 100;

    public long diamondPricing(Diamond diamond) {
        return 0;
    }

    @Override
    public void addDiamond(AddDiamondRequest addDiamondRequest) {

    }

    @Override
    public List<Diamond> listDiamonds() {
        List<Diamond> diamonds = diamondRepo.findAll();

        return diamonds;
    }

    @Override
    public GetDetailDiamondResponse getDetailDiamondResponse(long id) {
        Optional<Diamond> byId = diamondRepo.findById(id);
        GetDetailDiamondResponse getDetailDiamondResponse = new GetDetailDiamondResponse();
        BeanUtils.copyProperties(byId, getDetailDiamondResponse);
        return getDetailDiamondResponse;
    }

    @Override
    public int getDiamondPrice(double weight, DiamondClarityEnum clarityEnum, DiamondColorEnum diamondColorEnum) {
        Optional<Integer> price = rapaportReportRepository.getDiamondPrice(weight, clarityEnum.name(), diamondColorEnum.name());
        return BASE_PRICE_USD + price.orElse(0);
    }
}
