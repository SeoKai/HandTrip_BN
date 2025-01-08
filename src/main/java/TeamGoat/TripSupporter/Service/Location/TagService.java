package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.TagDto;
import TeamGoat.TripSupporter.Mapper.Location.TagMapper;
import TeamGoat.TripSupporter.Repository.Location.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream().map(tagMapper :: toDto).collect(Collectors.toList());
    }

}
