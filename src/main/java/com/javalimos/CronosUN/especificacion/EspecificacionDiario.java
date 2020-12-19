package com.javalimos.CronosUN.especificacion;

import com.javalimos.CronosUN.dto.DatosFiltroEntradasDiario;
import com.javalimos.CronosUN.modelo.EntradaDiario;
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
public class EspecificacionDiario implements Specification<EntradaDiario> {
    private DatosFiltroEntradasDiario criterioBusqueda;
    private Usuario usuario;
    
    @Override
    public Predicate toPredicate( Root<EntradaDiario> entradaDiario, CriteriaQuery<?> consulta,
                                  CriteriaBuilder criteriaBuilder ) {
        List<Predicate> filtros = new ArrayList<Predicate>();
        
        Predicate seleccionUsuario = criteriaBuilder.equal( entradaDiario.<Usuario>get( "usuario" ), usuario );
        filtros.add( seleccionUsuario );
        
        Optional.ofNullable( criterioBusqueda.getFechaInicio() ).ifPresent( ( fechaInicio ) -> {
            Predicate filtroFechaLimiteInicio = criteriaBuilder.greaterThanOrEqualTo( entradaDiario.<Date>get( "fecha" ), fechaInicio );
            filtros.add( criteriaBuilder.and( filtroFechaLimiteInicio ) );
        } );
        
        Optional.ofNullable( criterioBusqueda.getFechaFin() ).ifPresent( ( fechaFin ) -> {
            Predicate filtroFechaLimiteFin = criteriaBuilder.lessThanOrEqualTo( entradaDiario.<Date>get( "fecha" ), fechaFin );
            filtros.add( criteriaBuilder.and( filtroFechaLimiteFin ) );
        } );
        
        Optional.ofNullable( criterioBusqueda.getHoraInicio() ).ifPresent( ( horaInicio ) -> {
            Predicate filtroHoraLimiteInicio = criteriaBuilder.greaterThanOrEqualTo( entradaDiario.<Date>get( "hora" ), horaInicio );
            filtros.add( criteriaBuilder.and( filtroHoraLimiteInicio ) );
        } );
        
        Optional.ofNullable( criterioBusqueda.getHoraFin() ).ifPresent( ( horaFin ) -> {
            Predicate filtroHoraLimiteFin = criteriaBuilder.lessThanOrEqualTo( entradaDiario.<Date>get( "hora" ), horaFin );
            filtros.add( criteriaBuilder.and( filtroHoraLimiteFin ) );
        } );
        
        Optional.ofNullable( criterioBusqueda.getFechas() ).ifPresent( ( fechas ) -> {
            Predicate filtroFechasEspecificas = entradaDiario.get( "fecha" ).in( fechas );
            filtros.add( criteriaBuilder.and( filtroFechasEspecificas ) );
        } );
        
        return criteriaBuilder.and( filtros.toArray( new Predicate[ 0 ] ) );
    }
}
