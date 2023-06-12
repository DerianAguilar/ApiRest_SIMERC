package com.fesc.SIMERC.Repositories;

import com.fesc.SIMERC.Entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM tarea t WHERE t.usuario_id=:id AND t.estado= 0")
    public List<Tarea> misTareas (long id);

}
