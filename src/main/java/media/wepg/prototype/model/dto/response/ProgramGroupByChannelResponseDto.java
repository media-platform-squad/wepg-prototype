package media.wepg.prototype.model.dto.response;

import lombok.Data;
import media.wepg.prototype.model.es.Channel;
import media.wepg.prototype.model.es.Program;

import java.util.List;

@Data
public class ProgramGroupByChannelResponseDto {
    private ChannelResponseDto channelResponseDto;
    private List<ProgramResponseDto> programsResponseDto;

    public ProgramGroupByChannelResponseDto(Channel channel, List<Program> programs) {
        this.channelResponseDto = new ChannelResponseDto(channel);
        this.programsResponseDto = programs.stream()
                .map(Program::createDto)
                .toList();
    }
}
