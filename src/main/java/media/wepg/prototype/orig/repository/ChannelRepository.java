package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findAllBy();

    Optional<Channel> findAllById(Long id);
}
