package com.warthogcash.presupuesto.domain.model;

/**
 * Estados visuales de la barra de progreso de categoría.
 * Ver especificación de pantalla "Pantalla principal", sección 3.2.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/EstadoBarraProgreso;", "", "(Ljava/lang/String;I)V", "NORMAL", "CERCA_DEL_LIMITE", "LIMITE_SUPERADO", "app_debug"})
public enum EstadoBarraProgreso {
    /*public static final*/ NORMAL /* = new NORMAL() */,
    /*public static final*/ CERCA_DEL_LIMITE /* = new CERCA_DEL_LIMITE() */,
    /*public static final*/ LIMITE_SUPERADO /* = new LIMITE_SUPERADO() */;
    
    EstadoBarraProgreso() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.warthogcash.presupuesto.domain.model.EstadoBarraProgreso> getEntries() {
        return null;
    }
}