package com.fesc.SIMERC.Repositories;

import com.fesc.SIMERC.Entities.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM notas n WHERE n.usuario_id= :id")
    public List<Recordatorio> listarRecorUser(long id);
}
