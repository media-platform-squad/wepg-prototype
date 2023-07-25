package media.wepg.prototype.es.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import media.wepg.prototype.es.model.Program;

import java.time.LocalDateTime;

@Data
@Builder
public class ProgramResponseDto {

    private Long serviceId;
    private String eventId;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String titleName;
    private String synopsisName;
    private String directorName;
    private String actorName;
    private String songName;
    private String singerName;
    private String providerName;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public ProgramResponseDto(Program program) {
        this.serviceId = program.getServiceId();
        this.eventId = program.getEventId();
        this.eventStartDate = program.getEventStartDate();
        this.eventEndDate = program.getEventEndDate();
        this.titleName = program.getTitleName();
        this.synopsisName = program.getSynopsisName();
        this.directorName = program.getDirectorName();
        this.actorName = program.getDirectorName();
        this.actorName = program.getActorName();
        this.songName = program.getSongName();
        this.singerName = program.getSingerName();
        this.providerName = program.getProviderName();
        this.insertDate = program.getInsertDate();
        this.updateDate = program.getUpdateDate();
    }

}
