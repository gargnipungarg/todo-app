package org.opensource.todo.respository;

import org.opensource.todo.model.TODOEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TODOManagementRepository extends JpaRepository<TODOEntity, Long> {
}