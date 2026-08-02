# Changelog

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