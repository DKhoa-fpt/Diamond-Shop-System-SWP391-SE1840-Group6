package vn.fpt.diamond_shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.constants.UrlConstants;
import vn.fpt.diamond_shop.model.Diamond;
import vn.fpt.diamond_shop.request.CreateDiamondRequest;
import vn.fpt.diamond_shop.service.DiamondService;
import vn.fpt.diamond_shop.util.BaseResponse;
import vn.fpt.diamond_shop.util.logger.LogActivities;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(UrlConstants.BASIC_DIAMOND_URL)
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @GetMapping("/detail/")
    @LogActivities
    public ResponseEntity<?> detail() {
        return new ResponseEntity<>(diamondService.getDetailDiamondResponse(1), HttpStatus.OK);
    }

    @PostMapping("/create")
    @LogActivities
    public ResponseEntity<?> create(@Valid @RequestBody CreateDiamondRequest request, BindingResult bindingResult) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/list")
    @LogActivities
    public ResponseEntity<List<Diamond>> list() {
        return BaseResponse.ok(diamondService.listDiamonds());
    }

}
