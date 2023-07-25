package media.wepg.prototype.es.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.controller.response.common.ApiResponse;
import media.wepg.prototype.es.model.Program;
import media.wepg.prototype.es.model.dto.response.ProgramResponseDto;
import media.wepg.prototype.es.repository.EsProgramQuery;
import media.wepg.prototype.es.util.DateTimeConverter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wepg/program")
@RequiredArgsConstructor
public class EsProgramController {

    private final EsProgramQuery esProgramQuery;

    @PostMapping("/updateAllPrograms")
    public ApiResponse<Object> updateAllPrograms() throws IOException {
        esProgramQuery.fetchAndIndexProgramData();

        return ApiResponse.ok();
    }

    @GetMapping("/getPrograms")
    public ApiResponse<Object> getProgramsByServiceIdAndEventStartDate(
            @RequestParam("serviceId") Long serviceId,
            @RequestParam("eventStartDate") String dateString) {

        LocalDateTime eventStartDate = DateTimeConverter.convert(dateString);

        List<Program> programs = esProgramQuery.getDocumentByServiceIdAndEventStartDate(serviceId, eventStartDate);

        if (programs.isEmpty()) return ApiResponse.fail();
        return ApiResponse.ok(programs.stream().map(ProgramResponseDto::new));
    }

}