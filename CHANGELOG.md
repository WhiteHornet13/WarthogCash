# Changelog


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