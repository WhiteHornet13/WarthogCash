# Warthog Cash — Presupuesto Personal (Android)

Implementación Android nativa (Kotlin, XML Views, Room) del proyecto
**Presupuesto Personal**, siguiendo la especificación técnica y la
descripción de pantallas/mockups entregadas.

## Cómo abrir el proyecto

1. Abre Android Studio → **Open** → selecciona esta carpeta (`WarthogCash/`).
2. Deja que Gradle sincronice (descargará el wrapper de Gradle 8.6 y las
   dependencias la primera vez; requiere conexión a internet).
3. Ejecuta el módulo `app` en un emulador o dispositivo con **Android 8.0
   (API 26) o superior**.

No se incluye el binario `gradle-wrapper.jar` (no se pudo generar sin red
en el entorno donde se creó este proyecto). Android Studio lo
regenerará automáticamente al sincronizar, o puedes ejecutar
`gradle wrapper` manualmente si tienes Gradle instalado.

## Qué se implementó

Las 8 pantallas descritas en la especificación, con datos reales
persistidos en Room (no hay `SampleData.kt`: se pidió explícitamente la
implementación completa, no solo las pantallas con datos de ejemplo):

1. Bienvenida (primer uso)
2. Pantalla principal
3. Mis meses (con agrupación por año y paginación incremental)
4. Detalle de mes anterior (abierto)
5. Detalle de mes cerrado
6. Crear mes nuevo
7. Añadir gasto
8. Historial de gastos (genérico y filtrado por categoría)

Arquitectura en capas tal como exige la especificación técnica (sección 5.4):

```
ui/                    → Activities, ViewModels, Adapters (nunca tocan Room)
domain/model/          → Modelos de dominio (Presupuesto, Categoria, Gasto...)
domain/repository/     → Interfaz PresupuestoRepository
data/repository/       → Implementación; traduce entidades Room ↔ dominio
data/entity/           → Entidades Room (PresupuestoEntity, CategoriaEntity, GastoEntity)
data/dao/               → DAOs de Room
data/AppDatabase.kt     → Instancia central de Room
```

Componentes de interfaz reutilizados entre pantallas (sección 7):
`MonthSummaryHeaderView`, `CategoriaAdapter` (lista de categorías) y
`WarningBannerView`, todos en `ui/common/`.

## Asunciones y huecos de la especificación

La especificación es un documento vivo y deja explícitamente varios
puntos "pendientes de definir". Donde fue necesario tomar una decisión
para tener una app funcional, se optó por la alternativa más simple y
se documentó en el propio código (buscar comentarios `spec` o
`pendiente`). Las más relevantes:

- **SDK mínimo/objetivo**: no estaba decidido (sección 8). Se fija
  `minSdk 26` / `targetSdk 34` como valor razonable.
- **Inyección de dependencias**: no estaba decidido (sección 8). Se
  optó por instanciación manual vía la clase `App` (Application), sin
  Hilt/Koin, por ser la opción de menor complejidad para el alcance
  actual.
- **Validación de que los porcentajes sumen 100%** (spec "Crear mes
  nuevo", sección 5, "fuera de alcance"): se implementó la
  interpretación más directa — bloquear la creación si no suman
  exactamente 100, mostrando un mensaje de error.
- **Tamaño de página en "Mis meses"**: la spec pide un tamaño
  "dinámico" según lo que quepa en pantalla (sección 4.2). Se usa un
  tamaño fijo (8 meses) como aproximación razonable, ya que calcular el
  número exacto que cabe en pantalla depende de medidas de layout en
  tiempo de render.
- **Pantalla "Cerrar mes · paso de traspasos"**: se referencia varias
  veces desde "Detalle de mes anterior (abierto)" (sección 4.3), pero
  **no tiene especificación propia** en el documento de descripción de
  pantallas entregado (no hay mockup ni sección funcional para ella).
  Se implementó una versión mínima (`CloseMonthActivity`) que muestra
  el sobrante total del mes y permite cerrarlo, **sin mover fondos a
  ningún otro mes**, dejándolo documentado en la propia pantalla y en
  el código como una limitación a resolver en cuanto exista esa
  especificación.
- **Selección de mes/año en "Crear mes nuevo"**: la spec exige que sea
  manual (sección 4.1) pero no detalla el control visual. Se
  implementaron dos `Spinner` (mes / año) en el header de esa pantalla.
- **Color del punto junto al nombre de categoría**: los mockups
  muestran puntos de colores junto a cada categoría, pero la
  especificación técnica solo define colores por *estado* de gasto
  (normal / cerca del límite / límite superado), no colores fijos por
  categoría. Se interpretó como el color del estado, coherente con el
  resto de la pantalla.

## Notas de implementación adicionales

- `viewBinding` está activado; no se usa `findViewById` en ningún punto.
- Todas las cadenas de texto están en `strings.xml`, no hay literales
  embebidos salvo pequeños formateos numéricos.
- Los importes se muestran con `NumberFormat` en formato euro (es-ES),
  igual que en los mockups (`1.284,50 €`).
