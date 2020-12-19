package com.javalimos.CronosUN;

import com.javalimos.CronosUN.controlador.PortafolioControlador;
import com.javalimos.CronosUN.servicio.PortafolioServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest( PortafolioControlador.class )
public class PortafolioControladorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PortafolioServicio servicio;
    
    @Test
    public void validarRegistroProyecto() throws Exception {
        /*ProyectoDTO nuevoProyecto = new ProyectoDTO();
        nuevoProyecto.setTitulo( "Proyecto 1" );
        nuevoProyecto.setDescripcion( "Mio proyecto" );
        nuevoProyecto.setPrivacidad( false );
        nuevoProyecto.setEstado( "En progreso" );
        nuevoProyecto.setFechaCreacion( Date.valueOf( "2020-11-21" ) );
        nuevoProyecto.setCategoria( "Redes" );
        
        ObjectMapper mapeador = new ObjectMapper();
        String requestBody = mapeador.writeValueAsString( nuevoProyecto );
        
        RequestBuilder solicitud = MockMvcRequestBuilders
                .post( RutasApi.REGISTRO_PROYECTO )
                .contentType( MediaType.APPLICATION_JSON )
                .content( requestBody );
        MvcResult resultado = mockMvc.perform( solicitud ).andExpect( status().isOk() ).andReturn();*/
    }
    
    @Test
    public void validarActualizacionProyecto() throws Exception {
    
    }
    
    @Test
    public void validarEliminacionProyecto() throws Exception {
    
    }
    
    @Test
    public void validarConsultaProyecto() throws Exception {
    }
    
    @Test
    public void validarFiltroPredeterminadoProyectos() throws Exception {
    
    }
    
    @Test
    public void validarFiltroProyectosPorTiempo() throws Exception {
    
    }
    
    @Test
    public void validarFiltroProyectosPorTitulo() throws Exception {
    
    }
    
    @Test
    public void validarFiltroProyectosPorCategoria() throws Exception {
    
    }
    
    @Test
    public void validarFiltroProyectosPorEstado() throws Exception {
    
    }
    
    @Test
    public void validarFiltroProyectosPorTiempoTitulo() throws Exception {
    
    }
}
