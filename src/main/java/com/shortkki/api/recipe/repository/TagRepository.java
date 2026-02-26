package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.Tag;
import com.shortkki.api.recipe.entity.TagSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    Set<Tag> findAllByNameIn(Collection<String> names);

    List<Tag> findAllBySourceTypeOrderByNameAsc(TagSource sourceType);
}
