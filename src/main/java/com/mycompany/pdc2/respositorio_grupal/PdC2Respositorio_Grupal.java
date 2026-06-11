/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pdc2.respositorio_grupal;
import Vista.VistaLogin;
import Controlador.SistemaVentasControlador;
/**
 *
 * @author aniee
 */
public class PdC2Respositorio_Grupal {

    public static void main(String[] args) {
        SistemaVentasControlador ctrl = new SistemaVentasControlador();
        VistaLogin vista = new VistaLogin(ctrl);
        vista.setVisible(true);
    }
}
