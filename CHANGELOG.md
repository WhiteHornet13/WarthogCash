# Changelog

## [1.9.3] - 2026-08-13
### Fixed
- Al cerrar un mes con reparto de sobrante, el importe traspasado se
  contabilizaba como "gastado" en la categoría de origen sin distinguir
  su destino, mostrando siempre "límite superado" aunque el dinero no
  se hubiera gastado realmente. Ahora la categoría de origen muestra:
  - **"Restante ahorrado"** si el sobrante fue a Ahorro del mismo mes
    (no cuenta como gasto real en la categoría ni en cálculos).
  - **"Restante traspasado"** si el sobrante fue al mes siguiente (sí
    cuenta como gasto real a efectos de cálculo, aunque el dinero siga
    disponible en la categoría equivalente del mes destino).
  - "Límite superado" queda reservado para cuando el gasto real
    alcanza o supera lo asignado, sin traspaso de por medio.
- El "Disponible" de la cabecera del mes (`Presupuesto.totalRestante`)
  sumaba dos veces el dinero traspasado a Ahorro dentro del mismo mes
  (una vez ya reflejado en el restante de la categoría Ahorro, y otra
  vez al sumar `totalIngresosTraspasados` sobre el total del mes),
  inflando el importe disponible mostrado. Ahora se calcula como la
  suma de `restante` de cada categoría, evitando el doble conteo.
- Nuevos campos derivados `traspasadoAhorro` y `traspasadoOtroMes` en
  `Categoria`, y nuevas queries `sumarTraspasadoAAhorroPorCategoria` /
  `sumarTraspasadoOtroMesPorCategoria` en `GastoDao`, para poder
  distinguir ambos casos sin migración de esquema (se derivan de los
  campos `esTraspasoSalida`/`mesOrigenId` ya existentes).
- Nuevos valores `RESTANTE_TRASPASADO` y `RESTANTE_AHORRADO` en
  `EstadoBarraProgreso`, reflejados en `CategoriaAdapter` con color
  azul y verde respectivamente.

## [1.9.2] - 2026-08-13
### Fixed
- Los ingresos por traspaso (`esIngreso = true`, generados al cerrar un
  mes con reparto de sobrante) podían editarse o eliminarse manualmente
  desde "Historial de gastos" mientras el mes de destino siguiera
  abierto, ya que `ExpenseAdapter` solo comprobaba el estado del mes
  (`ABIERTO`/`CERRADO`), no si la fila era un traspaso. Ahora esos
  botones se ocultan también para filas de traspaso
  (`ExpenseAdapter.GastoViewHolder.bind()`).
- `PresupuestoRepositoryImpl.editarGasto()` / `eliminarGasto()` ahora
  rechazan la operación si `entidad.esIngreso` es `true`, como defensa
  en profundidad independiente de la UI. El único borrado válido de un
  traspaso sigue siendo el automático al eliminar el mes que lo generó
  (`eliminarTraspasosRecibidosDelMesSiguiente`,
  `revertirTraspasosRecibidosDelMesAnterior`, y la cascada de Room),
  que no pasan por estos métodos y no se ven afectados.

## [1.9.1] - 2026-08-10
### Fixed
- "Mis meses" → Eliminar mes: al eliminar un mes CERRADO que había
  traspasado sobrante al mes calendario siguiente, esos ingresos por
  traspaso no se borraban en el mes siguiente (dinero "fantasma" sin
  origen), ni se revertía el traspaso recibido en el mes anterior si
  este había traspasado sobrante hacia el mes que se elimina. La
  detección original comparaba el texto de la descripción del gasto
  para identificar cada traspaso, un método frágil que en la práctica
  no localizaba las filas a eliminar/revertir.
- Nuevo campo `mesOrigenId` en `GastoEntity`/`Gasto` (migración Room
  `4 → 5`): para las filas de traspaso entre meses, guarda el id del
  Presupuesto "al otro lado" del traspaso (mes de origen si es un
  ingreso recibido, mes de destino si es la salida registrada en el
  mes de origen). Sustituye la comparación de texto por una relación
  explícita y fiable. `cerrarMesConReparto` ahora rellena este campo
  al crear las filas de traspaso.
- `PresupuestoRepositoryImpl.eliminarMes()`: además de la cascada de
  categorías/gastos propios del mes, ahora también:
  - Elimina, en el mes calendario siguiente, los ingresos por
    traspaso originados por el mes que se elimina
    (`eliminarTraspasosRecibidosDelMesSiguiente`).
  - Revierte, en el mes calendario anterior (si está cerrado y
    traspasó sobrante a este mes), sus apuntes de salida —recuperando
    ese importe como "restante"— y lo reabre para que el usuario
    decida qué hacer con ese dinero
    (`revertirTraspasosRecibidosDelMesAnterior`).

## [1.9.0] - 2026-08-10
### Added
- "Mis meses": mantener pulsada una tarjeta de mes abre un menú de
  opciones:
  - **Eliminar mes** (disponible tanto en meses abiertos como
    cerrados): borra el mes junto con sus categorías y gastos
    (cascada ya existente vía `onDelete = CASCADE`). Si el mes
    eliminado estaba **cerrado** y había traspasado sobrante al mes
    calendario siguiente al cerrarse, esos ingresos por traspaso se
    eliminan también en el mes siguiente (evita dinero "fantasma" sin
    origen). Si el mes calendario **anterior** le había traspasado a
    su vez sobrante a este mes al cerrarse, ese traspaso se revierte
    (recupera el importe como "restante" en sus categorías de
    origen) y el mes anterior se **reabre**, para que el usuario
    decida qué hacer con ese dinero ahora que su destino ha
    desaparecido.
  - **Editar dinero disponible** (solo en meses abiertos, spec: un
    mes cerrado no se edita): permite cambiar el importe total del
    mes; los montos asignados por categoría se recalculan solos al
    derivarse de este valor en tiempo real.
  - Si el mes eliminado era el "actual", pasa a serlo el mes
    calendario más reciente que quede.
- Nuevos métodos en `PresupuestoRepository`/`PresupuestoRepositoryImpl`:
  `eliminarMes`, `actualizarDineroDisponible`; nuevos strings
  (`mis_meses_opcion_editar_dinero`, `mis_meses_opcion_eliminar`,
  `mis_meses_eliminar_confirmar_titulo`,
  `mis_meses_eliminar_confirmar_mensaje_formato`,
  `mis_meses_editar_dinero_titulo`).

## [1.8.2] - 2026-08-09
### Added
- "Gráficas", tipo 1 (Categorías por mes): cada gráfica de barras ahora
  superpone una línea roja con el monto asignado a la categoría cada
  mes (dinero del mes × % de la categoría + ingresos por traspaso
  recibidos), para comparar visualmente el gasto real contra lo
  presupuestado. Implementado con `CombinedChart` de MPAndroidChart en
  vez de `BarChart`; nuevo método `EstadisticasCalculator.asignadoPorCategoriaMensual`.

## [1.8.1] - 2026-08-09
### Fixed
- Hoja "Funciones" (Mis meses): el `BottomSheetDialog` usaba
  `ThemeOverlay.WarthogCash.Dialog` (pensado para `AlertDialog`), que no
  define `bottomSheetStyle`, dejando la parte superior sin esquinas
  redondeadas. Se elimina ese theme al construir el diálogo y se añade
  `android:background="@drawable/bg_card_rounded"` al layout
  `bottom_sheet_funciones.xml` como fondo propio.
- "Gráficas": los chips de "Años a comparar" (tipos 2, 4 y 5) no traían
  ningún año marcado por defecto, así que esas tres gráficas devolvían
  siempre "sin datos" al pulsar "Generar" sin tocar nada.
  `GraficasActivity.poblarSelectoresAnio()` marca ahora el año más
  reciente (`isChecked = anio == anios.last()`).
- "Gráficas": los 5 chips del selector de tipo de gráfica
  (`chipsTipoGrafica`) no eran `checkable`, por lo que nunca cambiaban
  de estado al pulsarlos y la app seguía generando siempre el Tipo 1 sin
  que el usuario pudiera notarlo. Añadido `android:checkable="true"` a
  los 5 chips en `activity_graficas.xml`, junto con
  `app:chipBackgroundColor`, `android:textColor` y
  `app:chipStrokeColor`/`app:chipStrokeWidth` (usando los selectores de
  color ya existentes en el proyecto) para que el chip seleccionado se
  distinga claramente del resto.

### Changed
- Icono del botón "Funciones" (`ic_functions.xml`): sustituido el glifo
  "F" por una rejilla 3x3, más reconocible como acceso a un menú de
  herramientas adicionales.

## [1.8.0] - 2026-08-07
### Added
- Nuevo botón "Funciones" en el header de "Mis meses", junto a Ajustes.
  Abre una hoja inferior con tres opciones: Gráficas, Exportar datos y
  Resumen anual.
- "Exportar datos": dos exportaciones independientes desde una nueva
  pantalla (`ExportActivity`):
  - Copia de seguridad completa en JSON (meses, categorías, gastos,
    gastos fijos y porcentajes predefinidos), restaurable en la propia
    app.
  - CSV del historial de gastos (fecha, mes, categoría, tipo, importe,
    descripción), pensado para abrir en Excel/OpenOffice. No sirve
    para restaurar datos en la app.
- "Restaurar copia de seguridad": permite recuperar un backup JSON.
  Solo disponible si hay 0 o 1 mes creado en la app (con 2 o más, hay
  que borrar los datos antes desde Ajustes). Con exactamente 1 mes
  (el mes inicial), se pregunta si sustituirlo por el contenido del
  backup o conservarlo; si se conserva y el backup trae ese mismo
  mes/año, gana el mes que ya existía en la app. Los gastos fijos y
  los porcentajes predefinidos del backup se importan siempre por
  completo, pudiendo solaparse con los que ya hubiera.
- "Gráficas": nueva pantalla con 5 tipos de gráfica seleccionables,
  todas basadas exclusivamente en meses **cerrados**:
  1. Gasto por categoría, mes a mes, en un año (barras, una gráfica
     por categoría).
  2. Gasto de una categoría en un mes concreto, comparado entre varios
     años (barras, una gráfica por categoría).
  3. Gasto, ahorro e ingreso mes a mes en un año (líneas, una gráfica
     por cada magnitud).
  4. Igual que el punto 3, comparando hasta 3 años a la vez (líneas
     multiserie).
  5. Gasto, ahorro e ingreso total anual, comparando 1 o más años
     (barras).
     Usa la librería MPAndroidChart (`com.github.PhilJay:MPAndroidChart`).
- Nuevo campo `esTraspasoSalida` en `GastoEntity`/`Gasto` (migración
  Room `3 → 4`) para distinguir un gasto real del apunte interno que
  `cerrarMesConReparto()` registra en la categoría de origen al
  traspasar su sobrante. Sin este flag, ese apunte se contabilizaba
  como gasto real en los nuevos cálculos de "Gasto total" de las
  gráficas y en el CSV exportado.
- Nuevos métodos en `PresupuestoRepository`/`PresupuestoRepositoryImpl`:
  `obtenerTodoParaBackup`, `contarMeses`, `restaurarBackup`; en
  `PresupuestoDao`: `obtenerTodos`, `eliminar`; utilidades nuevas
  `BackupJson`, `CsvExporter`, `EstadisticasCalculator`.

### Fixed
- "Ahorro" en las gráficas ya no se calculaba como los gastos reales
  registrados en la categoría Ahorro (lo que sería un "retiro"), sino
  como su `restante` real al cerrar el mes: asignado por % + ingresos
  por traspaso recibidos − gastos reales, que es lo que efectivamente
  queda ahorrado ese mes.

## [1.7.0] - 2026-08-07
### Added
- "Cerrar mes" ahora reparte el sobrante en vez de descartarlo:
  - Si existe el mes calendario inmediatamente siguiente y está abierto
    (p. ej. cerrar mayo cuando junio ya existe y está abierto), se muestra
    una lista con un checkbox por categoría: marcada traspasa su sobrante
    a la misma categoría de ese mes siguiente; desmarcada lo suma a Ahorro
    de este mismo mes. Ahorro nunca se traspasa a sí mismo.
  - Si no existe ese mes exacto (p. ej. cerrar junio cuando solo existe
    agosto, sin julio) o el siguiente ya está cerrado, no se muestra la
    lista: todo el sobrante va directo a Ahorro de este mes, categoría a
    categoría (una fila de ingreso independiente por cada categoría de
    origen, no un total agregado).
- Nuevo campo `esIngreso` en `GastoEntity`/`Gasto` (migración Room `2 → 3`)
  para distinguir un gasto real de un ingreso por traspaso. El "gastado"
  de una categoría solo suma gastos reales; el "asignado" pasa a ser
  `dinero_mes × porcentaje + ingresos por traspaso recibidos`.
- El historial de gastos muestra los ingresos por traspaso con signo `+`
  y color verde, en vez de forzar siempre el signo `−`.
- Nuevos métodos en `PresupuestoRepository`/`PresupuestoRepositoryImpl`:
  `existeMesSiguienteInmediatoAbierto`, `cerrarMesConReparto`; y en
  `PresupuestoDao`: `obtenerPorMesYAnio`; y en `GastoDao`:
  `sumarIngresosPorCategoria`.

### Fixed
- Los traspasos se registraban como gastos negativos en la propia tabla
  de gastos, dejando el "gastado" de la categoría destino en negativo en
  vez de aumentar su dinero disponible.
- Al cerrar un mes traspasando su sobrante, la categoría de ORIGEN seguía
  mostrando ese sobrante como "restante" disponible tras el cierre, pese
  a que ese mismo dinero ya aparecía también en el destino (Ahorro o mes
  siguiente): doble conteo. Ahora se registra la salida real en la
  categoría de origen, dejando su restante en 0.
- El importe "Disponible" del header y de las tarjetas de "Mis meses"
  (`Presupuesto.totalRestante`) no sumaba los ingresos recibidos por
  traspaso, mostrando siempre el dinero disponible original del mes tal
  cual se creó. Ahora es `dineroDisponible + totalIngresosTraspasados −
  totalGastado`.

## [1.6.0] - 2026-08-06
### Added
- "Historial de gastos": cada fila permite ahora editar o eliminar el
  gasto individual, para corregir errores al introducir el importe o
  la descripción. Ambas acciones piden confirmación mediante un
  diálogo, ya que recalculan el gasto de la categoría afectada.
  Disponible solo si el mes está abierto; en un mes cerrado los
  botones de editar/eliminar quedan ocultos.
- Nuevos iconos `ic_edit.xml` e `ic_delete.xml`.
- Nuevos métodos `editarGasto`/`eliminarGasto` en
  `PresupuestoRepository`/`PresupuestoRepositoryImpl`, y
  `actualizar`/`eliminar`/`obtenerPorId` en `GastoDao`.
- "Ajustes": el bloque "Porcentajes predefinidos" pasa a ser una fila
  plegable con el mismo estilo de acceso que "Gastos fijos" (icono
  `›` que se pulsa para expandir/contraer los 5 campos, en vez de
  mostrarlos siempre visibles).

### Fixed
- La Pantalla principal no reflejaba el nuevo importe "gastado" tras
  eliminar o editar un gasto desde el Historial (había que salir y
  volver a entrar en la app). `observarMesActual()` usa el Flow
  reactivo de Room, que solo se invalida con cambios en la tabla
  `presupuestos`, no en `gastos`. `MainViewModel` añade un
  `recargar()` explícito, invocado desde el nuevo
  `MainActivity.onResume()`, igual que ya hacían
  `MonthDetailActivity`/`MyMonthsActivity`.
- Los diálogos de confirmación (`AlertDialog`) de toda la app
  mostraban el título y el mensaje en verde, casi ilegibles, al
  heredar `colorPrimary`/`colorAccent` del tema general. Nuevo
  `ThemeOverlay.WarthogCash.Dialog` con colores de texto explícitos,
  aplicado en `ExpenseHistoryActivity`, `CloseMonthActivity` y
  `FixedExpensesActivity`.
- En "Editar gasto", el campo de importe mostraba el texto en blanco
  sobre fondo blanco (mismo problema ya corregido en 1.0.2 para los
  `EditText` definidos en XML, pero este se crea por código y no
  heredaba el arreglo). Añadido `textColor`/`hintTextColor` explícitos.
- En "Editar gasto", los campos de importe y descripción no tenían
  ningún borde visible. Añadido `bg_card_borde_suave` como fondo.
- En "Editar gasto", el campo de importe aceptaba `.` como separador
  decimal además de `,`, inconsistente con el resto de la app.
  Restringido con `DigitsKeyListener` y normalizado a `,` sobre la
  marcha, igual que en `AddExpenseActivity`/`CreateMonthActivity`.

## [1.5.0] - 2026-08-06
### Added
- Nueva funcionalidad "Gastos fijos": permite definir plantillas de
  gasto reutilizables mes a mes (Coste, Categoría y Comentario),
  gestionables desde una nueva pantalla accesible mediante el botón
  "Gastos fijos" añadido en "Ajustes" (crear, editar y eliminar).
- Nueva pantalla "Seleccionar gastos fijos": se abre automáticamente
  justo después de crear un mes nuevo, siempre que exista al menos un
  gasto fijo definido. Permite elegir qué gastos fijos se aplican a
  ese mes concreto (activados por defecto) y ajustar su importe solo
  para ese mes, sin modificar el gasto fijo original. Al confirmar, se
  generan automáticamente los gastos correspondientes en las
  categorías del mes recién creado.
- Si no existe ningún gasto fijo definido, "Crear mes nuevo" navega
  directamente a la Pantalla principal, igual que antes, sin mostrar
  la nueva pantalla de selección.
- Nueva tabla Room `gastos_fijos` (`GastoFijoEntity`, `GastoFijoDao`),
  con migración `1 → 2` de `AppDatabase` para no perder los datos ya
  existentes en instalaciones previas.
- Nuevos métodos en `PresupuestoRepository`/`PresupuestoRepositoryImpl`
  para gestionar gastos fijos y aplicarlos a un mes
  (`existenGastosFijos`, `obtenerGastosFijos`, `crearGastoFijo`,
  `actualizarGastoFijo`, `eliminarGastoFijo`, `aplicarGastosFijosAMes`).

## [1.4.0] - 2026-08-05
### Added
- "Crear mes nuevo": ya no se puede crear un mes posterior al siguiente
  al mes marcado como "actual" en la app. El límite avanza
  automáticamente cada vez que se crea ese siguiente mes, igual que la
  regla que ya aplicaba `PresupuestoRepositoryImpl.crearMes()` para
  decidir si un mes nuevo pasa a ser "actual". En el flujo de primer
  uso (sin ningún mes creado todavía) no se aplica este límite, ya que
  el selector queda fijado al mes real del dispositivo.
- "Crear mes nuevo": se impide crear dos veces un mes con el mismo
  mes/año (`PresupuestoRepository.existeMes`), evitando duplicados que
  dejaban dos meses distintos compitiendo por ser el "actual".

### Fixed
- "Mis meses": las tarjetas podían aparecer duplicadas al entrar en la
  pantalla si una carga incremental de paginación (disparada por
  scroll) terminaba después de que `onResume()` ya hubiera lanzado una
  recarga completa. `MyMonthsViewModel` ahora cancela cualquier carga
  en curso (`Job`) antes de lanzar una nueva, tanto en
  `cargarSiguientePagina()` como en `recargar()`.
- `PresupuestoRepositoryImpl.crearMes()`: el criterio para decidir si
  un mes nuevo pasa a ser "actual" comparaba contra el calendario real
  del dispositivo en lugar de contra el mes ya marcado como actual en
  base de datos, lo que podía dejar la regla de negocio desincronizada
  si el usuario no creaba un mes en cada mes real. Ahora se compara
  siempre contra el mes actual existente en Room (o se marca como
  actual sin más condiciones si todavía no hay ninguno, primer mes de
  la app).

## [1.3.0] - 2026-08-04
### Added
- "Crear mes nuevo": cuando se accede desde "Bienvenida" (primer uso,
  sin ningún mes creado todavía), los selectores de mes y año quedan
  fijados y deshabilitados en el mes actual del calendario del
  dispositivo. Evita crear el primer mes de la app con una fecha
  distinta a la actual, lo que dejaba la app sin ningún mes marcado
  como "actual" y la Pantalla principal en blanco sin botones
  funcionales.

### Fixed
- `crearMes()` (`PresupuestoRepositoryImpl`): si no existía todavía
  ningún mes marcado como "actual" en la base de datos (típicamente el
  primer mes de la app), el nuevo mes podía crearse sin marcarse como
  actual si su fecha no coincidía con el mes real o el siguiente,
  dejando la app sin ningún mes navegable. Ahora, si no existe un mes
  actual previo, el nuevo mes se marca como actual sin importar su
  fecha.
- "Crear mes nuevo": el mes seleccionado se calculaba a partir de la
  posición del `Spinner` (`selectedItemPosition + 1`) en lugar de su
  valor real. Al restringir el selector de mes a una sola opción en el
  flujo de primer uso, esto producía siempre "Enero" salvo que el mes
  real fuera enero, reintroduciendo el bug de mes sin marcar como
  actual. Corregido leyendo el mes por el texto seleccionado
  (`spinnerMes.selectedItem`), igual que ya se hacía con el año.
- "Mis meses": los meses aparecían duplicados al entrar en la
  pantalla. `MyMonthsViewModel` disparaba una carga de la primera
  página en su `init {}` y `MyMonthsActivity.onResume()` disparaba
  otra `recargar()` completa casi al mismo tiempo; al ser ambas
  corrutinas asíncronas sobre el mismo `StateFlow`, la primera página
  podía quedar añadida dos veces según el orden de finalización.
  Eliminado el `init {}` del ViewModel: la carga inicial ya queda
  cubierta por `onResume()`.

## [1.2.2] - 2026-08-04
### Fixed
- "Mis meses": al volver de cerrar un mes (o de cualquier detalle), la
  tarjeta no reflejaba el nuevo estado (color/badge) hasta salir por
  completo de la pantalla y volver a entrar. `MyMonthsViewModel` solo
  cargaba los meses una vez en `init`; ahora se añade `recargar()` y se
  invoca desde `onResume()` de `MyMonthsActivity`.
- "Detalle de mes": tras pulsar "Cerrar mes", el botón seguía visible
  al volver a esta pantalla porque su visibilidad se calculaba en un
  `collect` separado de `mostrarBotonCerrar`, que al no cambiar de
  valor no volvía a emitir junto con el nuevo estado del mes. Unificado
  en un único `collect` sobre `combine(viewModel.mes, viewModel.mostrarBotonCerrar)`
  en `MonthDetailActivity`, de forma que el botón se oculta de inmediato
  al cerrar el mes sin necesidad de reentrar en la pantalla.

## [1.2.1] - 2026-08-04
### Fixed
- "Detalle de mes" (abierto y cerrado): la lista de categorías no se
  pintaba porque el `RecyclerView` `listaCategorias` no tenía
  `LayoutManager` asignado, a diferencia del resto de listas de la app.
  Añadido `app:layoutManager` en `activity_month_detail.xml`.
- Cabecera de "Detalle de mes": el importe "Disponible" se solapaba con
  el título del mes y el botón de volver, porque estaba anclado a
  `tvEtiquetaDisponible`, una vista oculta (`GONE`) en el modo detalle.
  Sustituido por un `Barrier` (`barrierCabecera`) que ancla
  correctamente tanto en Pantalla principal como en Detalle de mes.
- El chip de estado ("● Mes abierto" / "● Mes cerrado") pasa a mostrarse
  a la derecha, a la misma altura que el importe "Disponible", en vez
  de ocupar su propia fila debajo y empujar el resto del contenido
  hacia abajo.

## [1.2.0] - 2026-08-04
### Changed
- "Mis meses": las tarjetas de cada mes ahora usan un color de fondo
  según su estado, en lugar de depender solo del badge "Actual" y el
  borde verde. Azul claro intenso para meses abiertos, rojo apagado
  para meses cerrados y naranja pastel intenso (con borde verde) para
  el mes actual. El color del texto de cada tarjeta (nombre, importe
  restante y subtítulo de gasto) se ajusta también por legibilidad
  sobre cada fondo.
- El badge "Actual" pasa a fondo verde con texto blanco para
  mantenerse legible sobre el nuevo fondo naranja de la tarjeta.

### Removed
- Drawables `bg_card_actual_borde.xml`, `bg_card_rounded_cerrado.xml`
  y `bg_card_actual_destacado.xml`, sustituidos por
  `bg_card_mes_abierto.xml`, `bg_card_mes_cerrado.xml` y
  `bg_card_mes_actual.xml`.

## [1.1.0] - 2026-08-03
### Added
- Nueva pantalla "Ajustes", accesible desde un icono de engranaje en el
  header de "Mis meses". De momento contiene los porcentajes
  predefinidos por categoría, pensada para acoger más opciones en el
  futuro.
- Porcentajes predefinidos por categoría: el usuario puede fijarlos
  desde "Ajustes" (persistidos en `SharedPreferences`, clase
  `PorcentajesPredefinidos`) y se precargan automáticamente al abrir
  "Crear mes nuevo", pudiendo modificarse libremente para ese mes en
  concreto.
- "Crear mes nuevo" ya no marca automáticamente como "mes actual" a
  cualquier mes creado: si el mes/año seleccionado es anterior al mes
  real del calendario del dispositivo, se crea correctamente pero el
  mes actual existente se mantiene sin cambios. Solo pasa a ser
  "actual" si coincide con el mes real o es el siguiente a este.

### Fixed
- La lista desplegable de los `Spinner` de mes y año en "Crear mes
  nuevo" mostraba texto ilegible (letras claras sobre fondo oscuro
  heredado del tema). Corregido fijando fondo claro explícito en
  `item_spinner_text.xml`.
- Los campos de importe (`Añadir gasto`, `Crear mes nuevo`) no
  aceptaban el `.` del teclado numérico como separador decimal,
  solo `,`, aunque el teclado del dispositivo mostrara el punto.
  Ahora se acepta cualquiera de los dos y se normaliza a `,`
  automáticamente mientras se escribe.
- En "Crear mes nuevo", pulsar Enter en el teclado numérico del campo
  "Dinero disponible" no ocultaba el teclado ni confirmaba el valor,
  a diferencia de "Añadir gasto". Corregido añadiendo
  `imeOptions="actionDone"`, `imeActionLabel`/`imeActionId` explícitos
  y gestión del evento de tecla Enter además del `actionId`, igual que
  en `AddExpenseActivity`.
- El botón "Crear mes" podía quedar oculto tras el teclado en algunos
  dispositivos. Corregido con `windowSoftInputMode="adjustResize"` en
  el manifest y `fillViewport="true"` en el `ScrollView`.
- El botón "+ Nuevo mes" de "Mis meses" mostraba un "+" duplicado (uno
  del texto y otro del icono del botón). Corregido quitando el "+" del
  string, dejando solo el icono nativo del componente.

## [1.0.2] - 2026-08-02
### Fixed
- El texto introducido en los campos de importe (`Añadir gasto`,
  `Crear mes nuevo`) y en la descripción de gasto se mostraba en
  blanco sobre fondo blanco, ilegible en algunos temas. Corregido
  fijando `android:textColor` y `android:textColorHint` explícitos
  en `etImporte`, `etDineroDisponible` y `etDescripcion`.
- El separador decimal de los campos de importe aceptaba tanto `.`
  como `,`, provocando inconsistencia con el formato es-ES usado en
  el resto de la app. Restringido con `android:digits` a solo aceptar
  coma como separador decimal.
- Los `Spinner` de mes y año en "Crear mes nuevo" usaban el layout de
  sistema `simple_spinner_dropdown_item`, cuyo color de texto podía
  resolver a blanco según el tema, dejándolos invisibles. Sustituido
  por un layout propio (`item_spinner_text.xml`) con color de texto
  fijo, tanto para el valor cerrado como para el desplegable.
- El string `crear_mes_error_porcentaje` usaba `&#37;` para
  representar el carácter `%`, lo cual no es válido al combinarse
  con un placeholder de formato (`%1$s`) al usarse como format
  string. Corregido usando `%%` en su lugar.
- En "Añadir gasto", pulsar Enter en el teclado numérico del campo
  de importe no hacía nada (había que usar la flecha de retroceso
  para cerrar el teclado). Añadido `android:imeOptions="actionDone"`
  y un listener que oculta el teclado al confirmar.

## [1.0.1] - 2026-08-02
### Fixed
- Las barras de progreso de categoría compartían el mismo `Drawable`
  (`progress_bar_categoria.xml`) entre todas las tarjetas del
  `RecyclerView`, provocando que solo se viera el color de la última
  categoría pintada. Corregido añadiendo `.mutate()` antes de
  `setTint()` en `CategoriaAdapter.kt` y en `MonthListAdapter.kt`.

### Changed
- El punto de color junto al nombre de cada categoría (Pantalla
  principal) pasa a representar la categoría en sí (color fijo por
  `TipoCategoria`), en lugar del estado de gasto. Colores asignados:
  General → verde principal, Ahorro → verde claro, Inversión → ámbar,
  Ocio → azul, Cultura → naranja. El borde de los chips en "Añadir
  gasto" usa ahora el mismo color por coherencia.