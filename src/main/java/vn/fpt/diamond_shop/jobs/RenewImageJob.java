package vn.fpt.diamond_shop.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.fpt.diamond_shop.model.Image;
import vn.fpt.diamond_shop.repository.ImageRepository;
import vn.fpt.diamond_shop.response.ImageInformation;
import vn.fpt.diamond_shop.service.ImageService;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class RenewImageJob {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    //run every 4 days
    @Scheduled(cron = "0 0 0 */4 * ?")
    public void renewImageJob() {
        renewImageTask();
    }

    public void renewImageTask() {
        List<Image> images = imageRepository.findAll();
        images.parallelStream().forEach(e -> {
            try {
                ImageInformation imageInformation = imageService.get(e.getImageName());
                e.setUrl(imageInformation.getUrl());
                e.setUpdateAt(OffsetDateTime.now(ZoneId.of("UTC")));
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
        imageRepository.saveAll(images);
    }

}
