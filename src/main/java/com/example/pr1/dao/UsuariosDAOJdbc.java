package com.example.pr1.dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.pr1.dto.matc0007DTO;

@Repository
public class UsuariosDAOJdbc implements UsuariosDAOInterface {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<matc0007DTO> mapper = (rs, rowNum) -> {
        matc0007DTO usuario = new matc0007DTO();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        return usuario;
    };

    @Override
    public List<matc0007DTO> leeUsuarios() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public boolean insertaUsuario(matc0007DTO usuario) {
        String sql = "INSERT INTO users (username,password) VALUES (?,?)";
        int filas = jdbcTemplate.update(sql,
                usuario.getUsername(),
                usuario.getPassword());
        return filas == 1;
    }

    @Override
    public matc0007DTO login(String username, String password) {

        String sql =
            "SELECT * FROM users WHERE username=? AND password=?";

        List<matc0007DTO> usuarios =
            jdbcTemplate.query(sql, mapper, username, password);

        if (usuarios.isEmpty()) {
            return null;
        }

        return usuarios.get(0);
    }
}