package de.alex.ghostnet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import de.alex.ghostnet.model.Geisternetz;
import de.alex.ghostnet.model.NetzStatus;

public interface NetzRepository extends JpaRepository<Geisternetz, Long> {
    List<Geisternetz> findByStatusInOrderByGemeldetAmDesc(List<NetzStatus> status);
    List<Geisternetz> findByBergendePersonIdOrderByGemeldetAmDesc(Long benutzerId);
    List<Geisternetz> findByStatusOrderByGemeldetAmDesc(NetzStatus status);
}
