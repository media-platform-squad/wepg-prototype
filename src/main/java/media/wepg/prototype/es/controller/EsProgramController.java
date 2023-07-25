package media.wepg.prototype.es.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.controller.response.common.ApiResponse;
import media.wepg.prototype.es.model.Program;
import media.wepg.prototype.es.model.dto.response.ProgramGroupByChannelResponseDto;
import media.wepg.prototype.es.model.dto.response.ProgramResponseDto;
import media.wepg.prototype.es.service.EsProgramService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wepg/program")
@RequiredArgsConstructor
public class EsProgramController {

   private final EsProgramService programService;

    @PostMapping("/updateAllPrograms")
    public ApiResponse<Object> updateAllPrograms() {
        try {
            programService.updateProgramData();
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return ApiResponse.ok();
    }

    @GetMapping("/getPrograms")
    public ApiResponse<Object> getProgramsByServicesAndEventStartDate(
            @RequestParam("serviceId") String serviceIds,
            @RequestParam("eventStartDate") String dateString) {

        List<ProgramGroupByChannelResponseDto> data = programService.getProgramsByServices(serviceIds, dateString);

        if (data.isEmpty()) {
            return ApiResponse.fail();
        }
           return ApiResponse.ok(data);
    }


    @GetMapping("/getPrograms")
    public ApiResponse<Object> getProgramsByServiceIdAndEventStartDate(
            @RequestParam("serviceId") String serviceIds,
            @RequestParam("eventStartDate") String dateString) {

        List<ProgramResponseDto> data = programService.getProgramsByServiceAndEventStartDate(serviceIds, dateString);

        if (data.isEmpty()) {
            return ApiResponse.fail();
        }
        return ApiResponse.ok(data);
    }

}