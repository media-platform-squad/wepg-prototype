package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.ProgramOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramOrigin, Long> {

    @Query("select pr from ProgramOrigin pr where pr.eventStartDate >= SYSDATE()-1 and pr.eventEndDate <= SYSDATE()+7 and pr.serviceId = :serviceId")
    List<ProgramOrigin> findAllByServiceId(@Param("serviceId") Long serviceId);

    @Query("SELECT pr FROM ProgramOrigin pr where pr.eventStartDate >= SYSDATE()-1 AND pr.eventEndDate <= SYSDATE()+7")
    Page<ProgramOrigin> findByEventStartDateAndEventEndDate(Pageable pageable);
}
