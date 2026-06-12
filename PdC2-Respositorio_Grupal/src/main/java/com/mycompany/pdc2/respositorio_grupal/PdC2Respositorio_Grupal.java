package com.mycompany.pdc2.respositorio_grupal;

import Controlador.SistemaVentasControlador;
import Vista.VistaZonas;
import Vista.VistaHistorialCliente;
import Vista.VistaRegistroTarjeta;

public class PdC2Respositorio_Grupal {

    public static void main(String[] args) {
        /*VistaConsola consola = new VistaConsola();
        consola.iniciar();
*/
        SistemaVentasControlador controlador = new SistemaVentasControlador();
        
       /* VistaZonas vistaZonas = new VistaZonas(null, controlador);
        vistaZonas.setVisible(true);*/
        
        
        
        
        /*VistaHistorialCliente vistaHistorial = new VistaHistorialCliente(null, controlador);
        vistaHistorial.setVisible(true);*/
        
        /*VistaRegistroTarjeta vistaTarjeta = new VistaRegistroTarjeta(null, true, controlador);
        vistaTarjeta.setVisible(true);*/
        
        System.exit(0);
    }
}
