package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("select pr from Program pr where pr.eventStartDate >= SYSDATE()-1 and pr.eventEndDate <= SYSDATE()+7 and pr.serviceId = :serviceId")
    List<Program> findByServiceId(Long serviceId);
}
