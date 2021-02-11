package com.myflight.app.repository;

import com.myflight.app.domain.Gate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Gate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
}
