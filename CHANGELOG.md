# Changelog

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