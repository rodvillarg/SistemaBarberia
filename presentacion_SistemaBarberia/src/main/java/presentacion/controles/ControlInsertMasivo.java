/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.controles;

import itson.negocios_insertmasivo.IInsertMasivo;
import itson.negocios_insertmasivo.InsertMasivo;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ControlInsertMasivo {
    
    private static ControlInsertMasivo instance;

    private static final IInsertMasivo insertMasivo = new InsertMasivo();

    public static void insertMasivo() {
        insertMasivo.insertMasivo();
    }
}
