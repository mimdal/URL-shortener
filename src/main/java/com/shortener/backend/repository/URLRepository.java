package com.shortener.backend.repository;

import com.shortener.backend.entity.URLEntity;
import org.springframework.data.jpa.repository.*;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
public interface URLRepository extends JpaRepository<URLEntity, Long> {
}
