# Changelog

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