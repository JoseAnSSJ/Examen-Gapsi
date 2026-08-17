# Examen Gapsi - Product Search App

Aplicación Android desarrollada como prueba técnica para Gapsi.

La aplicación permite buscar productos mediante la API de Walmart proporcionada por RapidAPI, visualizar los resultados en un grid, navegar entre las páginas disponibles y mantener un historial local de búsquedas.

## Funcionalidades

- Búsqueda de productos por palabra clave.
- Resultados mostrados en un grid de 3 columnas.
- Paginación manual mediante selector de páginas.
- Historial de búsquedas persistente.
- El historial se muestra al seleccionar el campo de búsqueda.
- Persistencia local del historial utilizando Room.
- Manejo de estados:
  - Loading.
  - Success.
  - Empty.
  - Error.
- Opción para reintentar una petición cuando ocurre un error.
- Imagen por defecto cuando la imagen de un producto no está disponible.
- Filtrado de productos que no contienen un precio válido.
- Formateo de precios en USD.
- Inyección de dependencias mediante Hilt.

---

## Tecnologías utilizadas

- Kotlin
- Android SDK
- XML Views
- View Binding
- MVVM
- Clean Architecture
- Hilt
- Retrofit
- OkHttp
- Gson
- Coroutines
- Flow / StateFlow
- Room
- RecyclerView
- ListAdapter / DiffUtil
- Glide
- Material Components

---

## Arquitectura

El proyecto sigue una separación basada en Clean Architecture:

```text
presentation
    │
    ├── ViewModel
    └── Adapters
          │
          ▼
domain
    │
    ├── Models
    ├── UseCases
    └── Repository interfaces
          │
          ▼
data
    │
    ├── Repository implementations
    │
    ├── remote
    │     ├── Retrofit
    │     ├── API Service
    │     └── DTOs
    │
    └── local
          ├── Room
          ├── DAO
          └── Entities
```

Esta separación permite mantener desacopladas la UI, las reglas de negocio y las fuentes de datos.

---

## Estructura del proyecto

```text
│
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   └── entity
│   │
│   ├── remote
│   │   └── dto
│   │
│   └── repository
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── di
│   ├── NetworkModule
│   ├── DatabaseModule
│   └── RepositoryModule
│
└── presentation
    └── adapter
```

---

## Búsqueda de productos

La búsqueda se realiza utilizando el endpoint:

```text
walmart-search-by-keyword
```

La petición recibe los parámetros:

```text
keyword
page
sortBy
```

El valor utilizado por defecto para ordenamiento es:

```text
best_match
```

La comunicación con la API se realiza mediante Retrofit y OkHttp.

---

## Paginación

La aplicación implementa paginación manual utilizando la información proporcionada por la API.

El número máximo de páginas se obtiene de:

```text
paginationV2.maxPage
```

Las páginas disponibles se muestran en un RecyclerView horizontal en la parte inferior de la pantalla.

Al seleccionar una página se realiza una nueva petición conservando la búsqueda actual.

---

## Historial de búsquedas

Las búsquedas realizadas se almacenan localmente utilizando Room.

Cada registro contiene:

```text
query
searchedAt
```

La consulta es utilizada como identificador único para evitar búsquedas duplicadas.

Cuando una búsqueda existente vuelve a realizarse, su fecha se actualiza para colocarla nuevamente entre las búsquedas más recientes.

El historial se ordena mediante:

```sql
ORDER BY searchedAt DESC
```

y se limita a las búsquedas más recientes.

---

## Manejo de precios

La respuesta de la API puede representar los precios de diferentes maneras.

La aplicación intenta obtener el precio utilizando el siguiente orden:

```text
CURRENT_PRICE
      ↓
DISCOUNTED_PRICE
      ↓
OPTIONS / OPTIONS_RANGE
```

Si un producto no contiene un precio válido, no se muestra en los resultados.

Los precios son formateados como moneda USD.

Ejemplo:

```text
$101.00
$1,249.99
```

---

## Manejo de imágenes

Las imágenes de los productos se cargan utilizando Glide.

Cuando una imagen no puede cargarse o no existe una URL válida, se utiliza una imagen por defecto mediante:

```kotlin
Glide.with(imageView.context)
    .load(imageUrl)
    .placeholder(R.drawable.ic_product_default)
    .error(R.drawable.ic_product_default)
    .into(imageView)
```

---

## Manejo de estados

La interfaz contempla diferentes estados de la búsqueda.

### Loading

Mientras se realiza una petición se muestra un indicador de progreso.

### Success

Los productos se muestran en un RecyclerView utilizando un GridLayoutManager de 3 columnas.

### Empty

Cuando la petición se completa correctamente pero no existen productos válidos, se muestra un mensaje indicando que no se encontraron resultados.

### Error

Si ocurre un error durante la petición, se muestra una pantalla de error junto con una opción para volver a intentar la búsqueda.

---

## Dependency Injection

La aplicación utiliza Hilt para gestionar la inyección de dependencias.

Los principales módulos son:

```text
NetworkModule
DatabaseModule
RepositoryModule
```

### NetworkModule

Proporciona las dependencias relacionadas con:

```text
OkHttpClient
Retrofit
ProductApiService
```

### DatabaseModule

Proporciona:

```text
AppDatabase
SearchHistoryDao
```

### RepositoryModule

Relaciona las interfaces del dominio con sus implementaciones:

```text
ProductRepository
        ↓
ProductRepositoryImpl

SearchHistoryRepository
        ↓
SearchHistoryRepositoryImpl
```

---


## Ejecución del proyecto

1. Clonar el repositorio.

```bash
git clone <repository-url>
```

2. Abrir el proyecto en Android Studio.

3. Agregar la API Key en:

```text
local.properties
```

```properties
RAPID_API_KEY=YOUR_API_KEY
```

4. Sincronizar las dependencias de Gradle.

5. Ejecutar la aplicación en un emulador o dispositivo Android con API 24 o superior.

---

## Requisitos

```text
Min SDK: 24
Target SDK: 35
Compile SDK: 35
Kotlin: 2.0.21
```

---

## Decisiones técnicas

Se utilizó MVVM junto con una separación por capas para evitar acoplar la interfaz con las fuentes de datos.

Retrofit y OkHttp son responsables de la comunicación con el servicio remoto, mientras que Room se utiliza como fuente de persistencia local para el historial.

Los Repository funcionan como abstracción entre las fuentes de datos y la capa de dominio.

StateFlow permite que la interfaz observe de manera reactiva los cambios de estado generados por el ViewModel.

Hilt centraliza la creación e inyección de dependencias, evitando que Activities y ViewModels conozcan cómo construir Retrofit, Room o las implementaciones de los Repository.

---

## Autor

Desarrollado como prueba técnica Android para Gapsi.
