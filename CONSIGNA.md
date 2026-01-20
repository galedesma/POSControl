# Challenge Java 2025
## Enunciado
Se requiere implementar una aplicación Java que exponga una API por HTTP para cumplir los siguientes casos de uso:

### 1. Caché de puntos de venta
La aplicación deberá contener un caché en memoria de "puntos de venta", de los que se conoce su identificador numérico y
el nombre de este.

Información inicial de puntos de venta:

| Id | Nombre     |
|----|------------|
| 1  | CABA       |
| 2  | GBA_1      |
| 3  | GBA_2      |
| 4  | Santa Fe   |
| 5  | Córdoba    |
| 6  | Misiones   |
| 7  | Salta      |
| 8  | Chubut     |
| 9  | Santa Cruz |
| 10 | Catamarca  |

Para este caché de puntos de venta se requerirán endpoints HTTP para:
1. Recuperar todos los puntos de venta en el caché
2. Ingresar un nuevo punto de venta
3. Actualizar un punto de venta
4. Borrar un punto de venta

### 2. Caché de costos entre los puntos de venta
También se deberá poseer un caché en memoria que guarde el costo numérico de ir de un punto de venta a otro. Se
comprende que:
- El costo nunca podría ser menor a 0.
- El costo de ir a un punto de venta a si mismo es cero.
- El costo de ir del punto de venta A al punto de venta B siempre será igual a hacer el camino inverso.
- No todos los puntos de venta están directamente conectados, y puede que un punto de venta B sea inalcanzable desde el
punto de venta A.
- Si un punto de venta A está comunicado directamente con un punto de venta B, ese camino es único y no posee un camino
directo paralelo.
- El camino directo más directo entre dos puntos puede no ser el menos costoso.

Información inicial de costos entre puntos de venta:

| IdA | IdB | Costo |
|-----|-----|-------|
| 1   | 2   | 2     |
| 1   | 3   | 3     |
| 2   | 3   | 5     |
| 2   | 4   | 10    |
| 1   | 4   | 11    |
| 4   | 5   | 5     |
| 2   | 5   | 14    |
| 6   | 7   | 32    |
| 8   | 9   | 11    |
| 10  | 7   | 5     |
| 3   | 8   | 10    |
| 5   | 8   | 30    |
| 10  | 5   | 5     |
| 4   | 6   | 11    |

Para este caché de costos entre puntos de venta se requerirán endpoints HTTP para:
1. Cargar un nuevo costo entre un punto de venta A y un punto de venta B (Crearía un camino directo entre A y B con el 
costo indicado).
2. Remover el costo entre un punto de venta A y un punto de venta B (removería, en caso de existir, un camino directo
entre A y B).
3. Consultar los puntos de venta directamente a un punto de venta A, y los costos que implica llegar a ellos.
4. Consultar el camino con costo mínimo entre dos puntos de venta A y B. Indicar el costo mínimo, y el camino realizado,
aprovechando los nombres de los puntos de venta del caché del punto anterior.

### 3. Acreditaciones
Además del control de caché de los puntos de venta, se requiere otro endpoint HTTP que reciba "acreditaciones".

La información relevante a la acreditación que se recibirá de forma externa es un importe y un indicador de punto de
venta.

Con esa información provista debemos enriquecer esta acreditación con:
1. La fecha en la que se recibió el pedido.
2. El nombre del punto de venta que le corresponde consultando el caché en memoria o fallando si el punto de venta no
existe.

Esta información enriquecida deberá ser persistida en una BBDD (preferentemente no en una memoria, y que sea externa a
la aplicación). Los atributos que deberán persistirse son:
- Importe
- Identificador de punto de venta
- Fecha de recepción
- Nombre de punto de venta

Esta información deberá ser consultable a través de otro endpoint HTTP.

## Como parte de la resolución
1. Indique cómo debe compilarse el código fuente, incluya comandos o scripts que deban tenerse en cuenta. Es deseable la
posibilidad de correr la aplicación en un contenedor Docker, pero no imperativo.
2. Indique cómo debe ejecutarse este compilado / imagen creada en el punto anterior y qué requisitos requiere el host
para poder realizarlo.
3. Indique cómo debe probarse la aplicación en los casos de uso del enunciado.
4. En caso de asumir supuestos, por favor déjenlos indicados.

## Tenga en cuenta
1. La aplicación entregada deberá poder soportar un alto grado de concurrencia sin que se presenten errores causados por
la implementación realizada.
2. Serán apreciadas inclusiones de nuevas utilidades disponibles en las últimas versiones de Java. Indicar cuales fueron
en caso de incluirse, cómo funcionan, en qué versión están incluidas.
3. Será apreciado el uso de patrones de diseño. Indicar cuáles fueron usados en caso de incluirse.
4. Será apreciado diagramas de clase / tablas / secuencias (que considere prácticos). No adjuntar archivos que requieren
de una herramienta específica para poder ser abiertos, salvo que la misma pueda ser fácilmente renderizada de forma 
natural y nativa en el repositorio entregado.
5. El código fuente debe contar con tests unitarios. Es deseable que se alcance un porcentaje de cobertura mayor al 70%,
sin incluir en la solución "smells", "bugs", "issues" o dependencias que posean vulnerabilidades críticas (esto último 
en la medida en que existan nuevas versiones que posean dichas vulnerabilidades corregidas). En lo posible adjunte un
reporte que indique estos resultados.
6. La resolución deberá ser presentada en un repositorio git con visibilidad pública para que pueda ser evaluado.
7. Es posible que distintos aspectos de la resolución deban ser defendidos durante una próxima entrevista técnica.