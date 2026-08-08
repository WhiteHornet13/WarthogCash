package com.warthogcash.presupuesto.util

import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.domain.model.PresupuestoConGastos
import org.json.JSONArray
import org.json.JSONObject
import com.warthogcash.presupuesto.domain.model.TipoCategoria

/**
 * Serialización de la copia de seguridad completa a JSON. Se usa org.json
 * (incluido en el SDK de Android) para no añadir una dependencia nueva solo
 * para esto, coherente con la instanciación manual ya usada en App.kt.
 */
object BackupJson {

    private const val VERSION_ACTUAL = 1

    fun generar(
        meses: List<PresupuestoConGastos>,
        gastosFijos: List<GastoFijo>,
        porcentajesPredefinidos: Map<TipoCategoria, Double>
    ): String {
        val raiz = JSONObject()
        raiz.put("version", VERSION_ACTUAL)
        raiz.put("generado", System.currentTimeMillis())

        val arrayMeses = JSONArray()
        meses.forEach { mes ->
            val jMes = JSONObject()
            jMes.put("mes", mes.mes)
            jMes.put("anio", mes.anio)
            jMes.put("dineroDisponible", mes.dineroDisponible)
            jMes.put("estado", mes.estado.name)
            jMes.put("esActual", mes.esActual)

            val arrayCategorias = JSONArray()
            mes.categorias.forEach { categoria ->
                val jCategoria = JSONObject()
                jCategoria.put("tipo", categoria.tipo.name)
                jCategoria.put("porcentaje", categoria.porcentaje)

                val arrayGastos = JSONArray()
                categoria.gastos.forEach { gasto ->
                    val jGasto = JSONObject()
                    jGasto.put("importe", gasto.importe)
                    jGasto.put("descripcion", gasto.descripcion)
                    jGasto.put("fecha", gasto.fecha)
                    jGasto.put("esIngreso", gasto.esIngreso)
                    jGasto.put("esTraspasoSalida", gasto.esTraspasoSalida)
                    arrayGastos.put(jGasto)
                }
                jCategoria.put("gastos", arrayGastos)
                arrayCategorias.put(jCategoria)
            }
            jMes.put("categorias", arrayCategorias)
            arrayMeses.put(jMes)
        }
        raiz.put("meses", arrayMeses)

        val arrayGastosFijos = JSONArray()
        gastosFijos.forEach { gf ->
            val jGastoFijo = JSONObject()
            jGastoFijo.put("coste", gf.coste)
            jGastoFijo.put("tipo", gf.tipo.name)
            jGastoFijo.put("comentario", gf.comentario)
            arrayGastosFijos.put(jGastoFijo)
        }
        raiz.put("gastosFijos", arrayGastosFijos)

        val jPorcentajes = JSONObject()
        porcentajesPredefinidos.forEach { (tipo, valor) -> jPorcentajes.put(tipo.name, valor) }
        raiz.put("porcentajesPredefinidos", jPorcentajes)

        return raiz.toString(2) // indentado: legible si se abre a mano
    }

    /** Resultado de parsear un archivo de copia de seguridad válido. */
    data class BackupParseado(
        val meses: List<com.warthogcash.presupuesto.domain.model.PresupuestoConGastos>,
        val gastosFijos: List<com.warthogcash.presupuesto.domain.model.GastoFijo>,
        val porcentajesPredefinidos: Map<com.warthogcash.presupuesto.domain.model.TipoCategoria, Double>
    )

    /** Excepción específica para poder distinguir "archivo no es un backup
     *  válido" de otros errores de IO en la pantalla de Exportar/Restaurar. */
    class BackupInvalidoException(mensaje: String) : Exception(mensaje)

    fun parsear(json: String): BackupParseado {
        val raiz = try {
            JSONObject(json)
        } catch (e: Exception) {
            throw BackupInvalidoException("El archivo no es un JSON válido")
        }

        val version = raiz.optInt("version", -1)
        if (version != VERSION_ACTUAL) {
            throw BackupInvalidoException("Versión de copia de seguridad no soportada")
        }

        val arrayMeses = raiz.optJSONArray("meses")
            ?: throw BackupInvalidoException("El archivo no contiene meses")

        val meses = (0 until arrayMeses.length()).map { i ->
            val jMes = arrayMeses.getJSONObject(i)
            val arrayCategorias = jMes.getJSONArray("categorias")

            val categorias = (0 until arrayCategorias.length()).map { j ->
                val jCategoria = arrayCategorias.getJSONObject(j)
                val arrayGastos = jCategoria.getJSONArray("gastos")

                val gastos = (0 until arrayGastos.length()).map { k ->
                    val jGasto = arrayGastos.getJSONObject(k)
                    com.warthogcash.presupuesto.domain.model.Gasto(
                        id = 0, // se regenera al insertar; el backup no depende de IDs concretos
                        categoriaId = 0, // idem: se resuelve al insertar la categoría real
                        importe = jGasto.getDouble("importe"),
                        descripcion = if (jGasto.isNull("descripcion")) null else jGasto.getString("descripcion"),
                        fecha = jGasto.getLong("fecha"),
                        esIngreso = jGasto.optBoolean("esIngreso", false),
                        esTraspasoSalida = jGasto.optBoolean("esTraspasoSalida", false)
                    )
                }

                com.warthogcash.presupuesto.domain.model.CategoriaConGastos(
                    tipo = com.warthogcash.presupuesto.domain.model.TipoCategoria.valueOf(jCategoria.getString("tipo")),
                    porcentaje = jCategoria.getDouble("porcentaje"),
                    gastos = gastos
                )
            }

            com.warthogcash.presupuesto.domain.model.PresupuestoConGastos(
                mes = jMes.getInt("mes"),
                anio = jMes.getInt("anio"),
                dineroDisponible = jMes.getDouble("dineroDisponible"),
                estado = com.warthogcash.presupuesto.domain.model.EstadoPresupuesto.valueOf(jMes.getString("estado")),
                esActual = jMes.getBoolean("esActual"),
                categorias = categorias
            )
        }

        val arrayGastosFijos = raiz.optJSONArray("gastosFijos") ?: JSONArray()
        val gastosFijos = (0 until arrayGastosFijos.length()).map { i ->
            val jGastoFijo = arrayGastosFijos.getJSONObject(i)
            com.warthogcash.presupuesto.domain.model.GastoFijo(
                id = 0,
                coste = jGastoFijo.getDouble("coste"),
                tipo = com.warthogcash.presupuesto.domain.model.TipoCategoria.valueOf(jGastoFijo.getString("tipo")),
                comentario = if (jGastoFijo.isNull("comentario")) null else jGastoFijo.getString("comentario")
            )
        }

        val jPorcentajes = raiz.optJSONObject("porcentajesPredefinidos")
        val porcentajesPredefinidos = if (jPorcentajes != null) {
            com.warthogcash.presupuesto.domain.model.TipoCategoria.entries
                .mapNotNull { tipo ->
                    if (jPorcentajes.has(tipo.name)) tipo to jPorcentajes.getDouble(tipo.name) else null
                }.toMap()
        } else {
            emptyMap()
        }

        return BackupParseado(meses, gastosFijos, porcentajesPredefinidos)
    }
}