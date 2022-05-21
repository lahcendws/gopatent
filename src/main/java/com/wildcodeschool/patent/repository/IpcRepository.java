package com.wildcodeschool.patent.repository;

import com.wildcodeschool.patent.entity.Ipc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpcRepository extends JpaRepository<Ipc, Long> {
    Optional<Ipc> findByIpc(String ipc);

    Boolean existsByIpc(String ipc);
}
