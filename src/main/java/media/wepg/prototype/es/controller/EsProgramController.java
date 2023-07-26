package media.wepg.prototype.es.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.controller.response.common.ApiResponse;
import media.wepg.prototype.es.model.dto.response.ProgramGroupByChannelResponseDto;
import media.wepg.prototype.es.model.dto.response.ProgramResponseDto;
import media.wepg.prototype.es.service.EsProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/wepg/program")
@RequiredArgsConstructor
public class EsProgramController {

    private final EsProgramService programService;

    @PostMapping("/updateAllPrograms")
    public ResponseEntity<Object> updateAllPrograms() {
        try {
            programService.updateProgramData();
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return ApiResponse.ok();
    }

    @GetMapping("/getPrograms")
    public ResponseEntity<Object> getProgramsByServicesAndEventStartDate(
            @RequestParam("serviceId") String serviceIds,
            @RequestParam("eventStartDate") String dateString) {

        List<ProgramGroupByChannelResponseDto> data = programService.getProgramsByServices(serviceIds, dateString);

        if (data.isEmpty()) {
            return ApiResponse.notFound();
        }
        return ApiResponse.ok(data);
    }


    @GetMapping("/getProgram")
    public ResponseEntity<Object> getProgramsByServiceIdAndEventStartDate(
            @RequestParam("serviceId") String serviceIds,
            @RequestParam("eventStartDate") String dateString) {

        List<ProgramResponseDto> data = programService.getProgramsByServiceAndEventStartDate(serviceIds, dateString);

        if (data.isEmpty()) {
            return ApiResponse.notFound();
        }
        return ApiResponse.ok(data);
    }

}