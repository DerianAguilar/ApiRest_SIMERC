package com.fesc.SIMERC.Repositories;

import com.fesc.SIMERC.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String correo);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM usuarios u WHERE u.nombre LIKE %:buscar% OR u.apellido LIKE %:buscar%  OR u.email LIKE %:buscar% OR u.documento LIKE %:buscar%"
    )
    public List<Usuario> buscarAumno(String buscar);

}
