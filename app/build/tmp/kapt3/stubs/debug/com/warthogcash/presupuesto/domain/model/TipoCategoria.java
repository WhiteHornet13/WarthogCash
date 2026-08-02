package com.warthogcash.presupuesto.domain.model;

/**
 * Las 5 categorías fijas de cada Presupuesto (mes).
 *
 * Especificación técnica, sección 5.2: los nombres de categoría están
 * definidos como un enum en código, no como datos en base de datos.
 * Solo el porcentaje asignado a cada una varía por mes y se almacena.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0010B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "", "etiqueta", "", "colorResId", "", "(Ljava/lang/String;ILjava/lang/String;I)V", "getColorResId", "()I", "getEtiqueta", "()Ljava/lang/String;", "GENERAL", "AHORRO", "INVERSION", "OCIO", "CULTURA", "Companion", "app_debug"})
public enum TipoCategoria {
    /*public static final*/ GENERAL /* = new GENERAL(null, 0) */,
    /*public static final*/ AHORRO /* = new AHORRO(null, 0) */,
    /*public static final*/ INVERSION /* = new INVERSION(null, 0) */,
    /*public static final*/ OCIO /* = new OCIO(null, 0) */,
    /*public static final*/ CULTURA /* = new CULTURA(null, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String etiqueta = null;
    private final int colorResId = 0;
    
    /**
     * Orden fijo en el que deben mostrarse siempre las 5 categorías.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.warthogcash.presupuesto.domain.model.TipoCategoria> ORDEN_VISUAL = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.domain.model.TipoCategoria.Companion Companion = null;
    
    TipoCategoria(java.lang.String etiqueta, int colorResId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEtiqueta() {
        return null;
    }
    
    public final int getColorResId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.warthogcash.presupuesto.domain.model.TipoCategoria> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/TipoCategoria$Companion;", "", "()V", "ORDEN_VISUAL", "", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "getORDEN_VISUAL", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Orden fijo en el que deben mostrarse siempre las 5 categorías.
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.warthogcash.presupuesto.domain.model.TipoCategoria> getORDEN_VISUAL() {
            return null;
        }
    }
}