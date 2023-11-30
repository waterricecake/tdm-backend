package tdm.tdmbackend.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
