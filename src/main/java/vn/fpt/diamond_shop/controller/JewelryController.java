package vn.fpt.diamond_shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.fpt.diamond_shop.constants.UrlConstants;
import vn.fpt.diamond_shop.request.CreateDiamondRequest;
import vn.fpt.diamond_shop.request.GetDetailJewelryRequest;
import vn.fpt.diamond_shop.request.GetListJewelryRequest;
import vn.fpt.diamond_shop.service.ImageService;
import vn.fpt.diamond_shop.service.Impl.ImageServiceImpl;
import vn.fpt.diamond_shop.service.JewelryService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UrlConstants.BASIC_JEWELRY_URL)
public class JewelryController extends BaseController {

    @Autowired
    private JewelryService jewelryService;

    @PostMapping("list")
    @LogActivities
    public ResponseEntity<Object> list(@Valid @RequestBody GetListJewelryRequest request) {
        return jewelryService.jewelries(request);
    }

    @PostMapping("detail")
    @LogActivities
    public ResponseEntity<Object> detail(@Valid @RequestBody GetDetailJewelryRequest request) {
        return ok(jewelryService.detailJewelry(request.getIdJewelry()));
    }

    @GetMapping("jewelry_type")
    @LogActivities
    public ResponseEntity<Object> type() {
        return ok(jewelryService.jewelryType());
    }

    @PostMapping(value = "create", consumes = {"multipart/form-data"})
    @LogActivities
    public ResponseEntity<Object> create(@RequestPart("request") CreateDiamondRequest request,
                                         @RequestPart("image") MultipartFile file) {
        request.setMultipartFile(file);
        return ok(jewelryService.createJewelry(request));
    }

    @PostMapping(value = "update", consumes = {"multipart/form-data"})
    @LogActivities
    public ResponseEntity<Object> update(@RequestPart("request") CreateDiamondRequest request
            , @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        request.setMultipartFile(file);
        return ok(jewelryService.updateJewelry(request));
    }

    @DeleteMapping(value = "delete/{id}")
    @LogActivities
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return ok(jewelryService.deleteJewelry(id));
    }

}
