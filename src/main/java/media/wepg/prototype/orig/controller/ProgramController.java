package media.wepg.prototype.orig.controller;

import media.wepg.prototype.orig.model.Program;
import media.wepg.prototype.orig.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/orig/wepg/programs")
public class ProgramController {

    private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<List<Program>> getProgramsByServiceId(@PathVariable("serviceId") Long serviceId) {
        List<Program> programs = programService.getProgramsByServiceId(serviceId);

        if (programs.isEmpty()) {
            logger.info(serviceId + "번 채널이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info(serviceId + "번 채널에 " + programs.size() + "개의 프로그램 정보가 존재합니다.");
            return ResponseEntity.ok()
                    .body(programs);
        }
    }
}
