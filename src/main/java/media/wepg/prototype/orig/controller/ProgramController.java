package media.wepg.prototype.orig.controller;

import lombok.extern.slf4j.Slf4j;
import media.wepg.prototype.orig.model.ProgramOrigin;
import media.wepg.prototype.orig.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/orig/wepg/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<List<ProgramOrigin>> getProgramsByServiceId(@PathVariable("serviceId") Long serviceId) {
        List<ProgramOrigin> programOrigins = programService.getProgramsByServiceId(serviceId);

        if (programOrigins.isEmpty()) {
            log.info(serviceId + "번 채널이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }

        log.info(serviceId + "번 채널에 " + programOrigins.size() + "개의 프로그램 정보가 존재합니다.");
        return ResponseEntity.ok()
                .body(programOrigins);
    }
}
