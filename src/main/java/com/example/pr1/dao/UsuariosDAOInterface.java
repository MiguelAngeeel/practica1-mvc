package com.example.pr1.dao;

import java.util.List;
import com.example.pr1.dto.matc0007DTO;

public interface UsuariosDAOInterface {

    public List<matc0007DTO> leeUsuarios();

    public boolean insertaUsuario(matc0007DTO usuario);

    public matc0007DTO login(String username, String password);

}