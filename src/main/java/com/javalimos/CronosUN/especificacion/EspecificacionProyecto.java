package com.javalimos.CronosUN.especificacion;

import com.javalimos.CronosUN.dto.DatosFiltroProyectos;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EspecificacionProyecto implements Specification<Proyecto> {
    
    private final DatosFiltroProyectos criterioBusqueda;
    private Usuario usuario;
    
    @Override
    public Predicate toPredicate( Root<Proyecto> proyecto, CriteriaQuery<?> consulta,
                                  CriteriaBuilder criteriaBuilder ) {
        List<Predicate> filtros = new ArrayList<>();
        
        Predicate seleccionUsuario = criteriaBuilder.equal( proyecto.<Usuario>get( "usuario" ), usuario );
        filtros.add( seleccionUsuario );
    
        Optional.ofNullable( criterioBusqueda.getTitulo() ).ifPresent( ( titulo ) -> {
            Predicate filtroTitulo = criteriaBuilder.like( proyecto.<String>get( "titulo" ), "%" + titulo + "%" );
            filtros.add( filtroTitulo );
        } );
    
        Optional.ofNullable( criterioBusqueda.getFechaInicio() ).ifPresent( ( fechaInicio ) -> {
            Predicate filtroFechaLimiteInicio = criteriaBuilder.greaterThanOrEqualTo( proyecto.<Date>get( "fechaCreacion" ), fechaInicio );
            filtros.add( filtroFechaLimiteInicio );
        } );
    
        Optional.ofNullable( criterioBusqueda.getFechaFin() ).ifPresent( ( fechaFin ) -> {
            Predicate filtroFechaLimiteFin = criteriaBuilder.lessThanOrEqualTo( proyecto.<Date>get( "fechaCreacion" ), fechaFin );
            filtros.add( filtroFechaLimiteFin );
        } );
    
        Optional.ofNullable( criterioBusqueda.getPrivado() ).ifPresent( ( privado ) -> {
            Predicate filtroPrivacidad = criteriaBuilder.equal( proyecto.<Boolean>get( "privacidad" ), privado.booleanValue() );
            filtros.add( filtroPrivacidad );
        } );
    
        Optional.ofNullable( criterioBusqueda.getEstado() ).ifPresent( ( estado ) -> {
            if ( !estado.isEmpty() ) {
                Predicate filtroEstado = criteriaBuilder.equal( proyecto.<String>get( "estado" ), estado );
                filtros.add( filtroEstado );
            }
        } );
    
        Optional.ofNullable( criterioBusqueda.getCategorias() ).ifPresent( ( categorias ) -> {
            if ( !categorias.isEmpty() ) {
                Predicate filtroCategorias = proyecto.<String>get( "categoria" ).in( categorias );
                filtros.add( filtroCategorias );
            }
        } );
        
        return criteriaBuilder.and( filtros.toArray( new Predicate[ 0 ] ) );
    }
}
