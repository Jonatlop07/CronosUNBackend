package com.javalimos.CronosUN.repositorio;

import com.javalimos.CronosUN.modelo.EntradaDiario;
import com.javalimos.CronosUN.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntradaDiarioRepository extends PagingAndSortingRepository<EntradaDiario, Integer> {
    Integer countByUsuario( Usuario usuarioActual );
    
    Page<EntradaDiario> findAll( Specification<EntradaDiario> entradaDiarioSpecification, Pageable paginacion );
    
    Page<EntradaDiario> findByUsuario( Usuario usuario, Pageable paginacion );
    
    Optional<EntradaDiario> findByIdAndUsuario( Integer idEntrada, Usuario usuario );
}