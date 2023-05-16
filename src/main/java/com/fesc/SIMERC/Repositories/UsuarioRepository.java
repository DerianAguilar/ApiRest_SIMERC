package com.fesc.SIMERC.Repositories;

import com.fesc.SIMERC.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String correo);

}
