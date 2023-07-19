package media.wepg.prototype.orig.controller;

import jakarta.transaction.Transactional;
import media.wepg.prototype.orig.model.Program;
import media.wepg.prototype.orig.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/orig/wepg/programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<List<Program>> getProgramsByServiceId(@PathVariable Long serviceId){
        List<Program> programs = programService.getProgramsByServiceId(serviceId);

        return ResponseEntity.ok()
                .body(programs);
    }
}
