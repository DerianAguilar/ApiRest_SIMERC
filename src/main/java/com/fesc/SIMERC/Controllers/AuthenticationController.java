package com.fesc.SIMERC.Controllers;

import com.fesc.SIMERC.Entities.Rol;
import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Models.AdminResponse;
import com.fesc.SIMERC.Models.Peticiones.JwtRequest;
import com.fesc.SIMERC.Models.Respuestas.JwtResponse;
import com.fesc.SIMERC.Security.Exceptions.Mensaje;
import com.fesc.SIMERC.Security.Exceptions.MyException;
import com.fesc.SIMERC.Security.JwtUtils;
import com.fesc.SIMERC.Services.Impl.UserDatailsImpl;
import com.fesc.SIMERC.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private UserDatailsImpl userDatailsService;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest){


        String mensaje = autenticar(jwtRequest.getEmail(),jwtRequest.getPassword(),jwtRequest.getRol());
        System.out.println("------- "+jwtRequest.getRol()+" -------- "+jwtRequest.getEmail()+" -------- "+jwtRequest.getPassword());
        switch (mensaje){
            case "OK":
                UserDetails userDetails =  this.userDatailsService.loadUser(jwtRequest.getEmail(),jwtRequest.getRol());
                String token = this.jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            case "FAIL":
                throw new MyException("Credenciales invalidas");
            case "NX":
                return new ResponseEntity<>(new Mensaje("No existe ningun usuario con esas credenciales"), HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(new Mensaje("..."),HttpStatus.NOT_FOUND);
        }

    }

    private String autenticar(String username,String password,String rol) {

        List<Usuario> user = usuarioService.listarUsuario();
        if (!user.isEmpty()){
            String msj="";
            for (Usuario users:user){
                if (passwordEncoder.matches(password, users.getPass()) && Objects.equals(users.getRol().getRolNombre(), rol)&&
                        Objects.equals(users.getEmail(), username)) {
                    msj = "OK";
                    break;
                }else {
                    msj = "FAIL";
                }
            }

            return msj;
        }else {
            return "NX";
        }

    }

    @PostMapping("/actual-usuario")
    public Usuario obtenerUsuarioActual(@RequestBody JwtRequest principal){
        return (Usuario) this.userDatailsService.loadUser(principal.getEmail(), principal.getRol());
    }

    @GetMapping("/save-admin")
    public ResponseEntity<AdminResponse> guardarAdmin() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("admin");
        usuario.setApellido("fesc");
        usuario.setDocumento("800235151");
        usuario.setTelefono("+573227613865");
        usuario.setEmail("adminFesc@fesc.edu.co");
        String pass = "AdminSimerc";
        usuario.setPass(passwordEncoder.encode(pass));

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setRolNombre("ADMIN");

        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioService.guardarAdmin(usuario,rol);
        System.out.println(usuarioGuardado.getNombre());

        return ResponseEntity.ok(new AdminResponse(usuario.getEmail(), pass));
    }

}
