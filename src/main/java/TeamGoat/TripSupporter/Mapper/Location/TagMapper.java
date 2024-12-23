package TeamGoat.TripSupporter.Mapper.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.TagDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return TagDto.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getTagName())
                .build();
    }
}
