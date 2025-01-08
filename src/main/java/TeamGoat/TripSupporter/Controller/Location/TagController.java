package TeamGoat.TripSupporter.Controller.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.TagDto;
import TeamGoat.TripSupporter.Service.Location.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Slf4j
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/getAll")
    public List<TagDto> getAllTags(){
        log.info("get /tag/getAll called");
        return tagService.getAllTags();
    }
}
