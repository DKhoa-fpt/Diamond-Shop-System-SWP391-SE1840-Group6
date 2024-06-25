package vn.fpt.diamond_shop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.fpt.diamond_shop.model.Jewelry;
import vn.fpt.diamond_shop.model.JewelryType;
import vn.fpt.diamond_shop.repository.JewelryRepository;
import vn.fpt.diamond_shop.repository.JewelryTypeRepository;
import vn.fpt.diamond_shop.request.CreateDiamondRequest;
import vn.fpt.diamond_shop.request.GetListJewelryRequest;
import vn.fpt.diamond_shop.response.*;
import vn.fpt.diamond_shop.service.JewelryService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JewelryServiceImpl implements JewelryService {
    @Autowired
    private JewelryRepository jewelryRepository;

    @Autowired
    private JewelryTypeRepository jewelryTypeRepository;


    private static String JEWELRY_CODE_DEFAULT = "DMS_";
    private static Integer ACTIVE_VALUE = 1;

    @Autowired
    private ImageServiceImpl imageService;

    @Override
    public ResponseEntity<Object> jewelries(GetListJewelryRequest request) {
        List<GetListJewelryResponse>jewelries = new ArrayList<>();
        if(request.getLimit() == null){
            request.setLimit(10);
        }
        if(request.getOffset() == null){
            request.setOffset(0);
        }
        Page<GetListJewelryResponse> jewelriesPage =  jewelryRepository.getListJewelry(request.getJewelryTypeId(), request.getTitle(), request.getBudget1(), request.getBudget2(), request.getBudget3(), PageRequest.of((int )(request.getOffset()/request.getLimit()), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));
        jewelries = jewelriesPage.getContent();
        Meta meta = new Meta(request.getRequestId(), 200, "success", HttpStatus.OK.toString());
        meta.setLimit(request.getLimit());
        meta.setOffset(request.getOffset());
        meta.setTotal(Integer.valueOf(String.valueOf(jewelriesPage.getTotalElements()))) ;
        BaseResponse response = new BaseResponse(meta,jewelries);

        return ResponseEntity.ok(response);
    }

    @Override
    public GetDetailJewelryResponse detailJewelry(Long id) {
        return jewelryRepository.getDetailJewelry(id);
    }

    @Override
    public List<JewelryType> jewelryType() {
        return jewelryTypeRepository.findAll();
    }

    @Override
    public boolean createJewelry(CreateDiamondRequest request) {
        ImageInformation imageInformation = imageService.push(request.getMultipartFile());

        Jewelry jewelry = new Jewelry();
        BeanUtils.copyProperties(request, jewelry);
        jewelry.setJewelryCode(jewelryCode());
        jewelry.setCreatedBy("Khoa Tran");
        jewelry.setMaterialPrices(request.getMaterialPrices().longValue());
        jewelry.setJewelryTypeId(request.getJewelryTypeId());
        jewelry.setIdGuide(1L);
        jewelry.setImageId(imageInformation.getImageId());
        jewelry.setIsActive(ACTIVE_VALUE);
        java.util.Date date = new java.util.Date();
        jewelry.setCreatedAt(new Date(date.getTime()));
        jewelryRepository.save(jewelry);
        return true;
    }

    @Override
    public boolean updateJewelry(CreateDiamondRequest request) {
        ImageInformation imageInformation = null;
        if (request.getMultipartFile() != null) {
             imageInformation = imageService.push(request.getMultipartFile());
        }

        Jewelry jewelry = jewelryRepository.findJewelryById(request.getId());
        if (jewelry !=null) {
            jewelry.setName(request.getName());
            jewelry.setDescription(request.getDescription());
            jewelry.setQuantity(request.getQuantity());
            jewelry.setMaterialPrices(request.getMaterialPrices().longValue());
            if (imageInformation != null) {
                jewelry.setImageId(imageInformation.getImageId());
            }
            jewelryRepository.save(jewelry);
        }
        return true;
    }

    @Override
    public boolean deleteJewelry(Long id) {
        jewelryRepository.deleteById(id);
        return true;
    }

    private String jewelryCode() {
        long count = jewelryRepository.count();
        return JEWELRY_CODE_DEFAULT + count;
    }

}
