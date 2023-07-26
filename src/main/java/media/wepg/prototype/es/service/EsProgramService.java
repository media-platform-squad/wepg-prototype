package media.wepg.prototype.es.service;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.model.Program;
import media.wepg.prototype.es.model.dto.response.ProgramGroupByChannelResponseDto;
import media.wepg.prototype.es.model.dto.response.ProgramResponseDto;
import media.wepg.prototype.es.repository.EsChannelQuery;
import media.wepg.prototype.es.repository.EsProgramQuery;
import media.wepg.prototype.es.util.DateTimeConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EsProgramService {
    private final EsProgramQuery esProgramQuery;
    private final EsChannelQuery esChannelQuery;

    private static final String DELIMITER = ",";


    public void updateProgramData() throws IOException {
        esProgramQuery.fetchAndIndexProgramData();
    }

    public List<ProgramGroupByChannelResponseDto> getProgramsByServices(String serviceIds, String dateString) {
        LocalDateTime eventStartDate = DateTimeConverter.convert(dateString);

        List<ProgramGroupByChannelResponseDto> data = new ArrayList<>();
        String[] ids = serviceIds.strip().split(DELIMITER);

        Arrays.stream(ids).map(Long::valueOf).forEach(idValue -> {
            List<Program> programs = esProgramQuery.getDocumentByServiceIdAndEventStartDate(idValue, eventStartDate);
            Optional<Channel> channelById;
            channelById = getChannel(idValue);

            if (channelById.isPresent() && programs.isEmpty()) {
                data.add(new ProgramGroupByChannelResponseDto(channelById.get(), programs));
            }
        });

        return data;
    }

    private Optional<Channel> getChannel(Long idValue) {
        Optional<Channel> channelById;
        try {
            channelById = esChannelQuery.getDocumentById(idValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return channelById;
    }

    public List<ProgramResponseDto> getProgramsByServiceAndEventStartDate(String serviceIds, String dateString) {
        LocalDateTime eventStartDate = DateTimeConverter.convert(dateString);

        List<ProgramResponseDto> data = new ArrayList<>();
        String[] ids = serviceIds.strip().split(DELIMITER);

        Arrays.stream(ids).forEach(id -> {
            Long idValue = Long.valueOf(id);
            List<Program> programsByServiceId = esProgramQuery.getDocumentByServiceIdAndEventStartDate(idValue, eventStartDate);

            if (!programsByServiceId.isEmpty()) {
                programsByServiceId.forEach(program -> data.add(new ProgramResponseDto(program)));
            }
        });

        return data;
    }
}
