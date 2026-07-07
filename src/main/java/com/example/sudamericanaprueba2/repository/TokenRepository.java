package com.example.sudamericanaprueba2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import ch.qos.logback.core.subst.Token;
import com.example.sudamericanaprueba2.entity.Token;


import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
        select t from Token t 
        where t.usuario.userId = :adminId and (t.expired = false or t.revoked = false)
        """)
    List<Token> findAllValidTokensByUser(Long adminId);

    Optional<Token> findByToken(String token);
}