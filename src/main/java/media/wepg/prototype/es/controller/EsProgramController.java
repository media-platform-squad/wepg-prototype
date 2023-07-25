package media.wepg.prototype.es.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.model.Program;
import media.wepg.prototype.es.repository.EsProgramQuery;
import media.wepg.prototype.es.util.DateTimeConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wepg/program")
@RequiredArgsConstructor
public class EsProgramController {

    private final EsProgramQuery esProgramQuery2;

    @PostMapping("/updateAllPrograms")
    public ResponseEntity updateAllPrograms() throws IOException {
        esProgramQuery2.fetchAndIndexProgramData();

        return ResponseEntity.ok()
                .body("All programs were updated");
    }

    @GetMapping("/getPrograms")
    public ResponseEntity getProgramsByServiceIdAndEventStartDate(
            @RequestParam("serviceId") Long serviceId,
            @RequestParam("eventStartDate") String dateString) {

        LocalDateTime eventStartDate = DateTimeConverter.convert(dateString);

        List<Program> documentByServiceIdAndEventStartDate =
                esProgramQuery2.getDocumentByServiceIdAndEventStartDate(serviceId, eventStartDate);

        if (documentByServiceIdAndEventStartDate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(documentByServiceIdAndEventStartDate.stream().map(d -> d.getServiceId() + "_" + d.getEventStartDate()));
    }


}