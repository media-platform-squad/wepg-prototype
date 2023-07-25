package media.wepg.prototype.es.model.dto.response;

import lombok.Data;
import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.model.Program;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProgramGroupByChannelResponseDto {
    private ChannelResponseDto channelResponseDto;
    private List<ProgramResponseDto> programsResponseDto;

    public ProgramGroupByChannelResponseDto(Channel channel, List<Program> programs) {
        this.channelResponseDto = new ChannelResponseDto(channel);
        this.programsResponseDto = programs.stream()
                .map(Program::createDto)
                .collect(Collectors.toList());
    }
}
