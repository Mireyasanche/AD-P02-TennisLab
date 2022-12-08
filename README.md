# AD-P02-TennisLab - 09/12/2022

## · Repositorio original del proyecto
> https://github.com/Mireyasanche/AD-P02-TennisLab

## · Enlace al vídeo
> https://drive.google.com/file/d/1uMV5g0tLffVAKTA12k-_hMmrFOlZXWMJ/view?usp=share_link

## · Documentación
> https://iesluisvives-my.sharepoint.com/:w:/g/personal/mireya_sanchez_alumno_iesluisvives_org/EWT6k71eUAJMjFaQk2aG3P8BmJvsjkmS9WcJzwMC6Q9sNg?e=hkevi9

<br>
<hr>
<br>

### Colaboradores
> Realizado por: 
>> Alejandro Sánchez Monzón
>
>> Mireya Sánchez Pinzón.
>
> 2º DAM - Asignatura de Acceso a Datos.

<br>
<hr>
<br>

### Explicación del proyecto
> En esta práctica se nos planteaba la situación de un cliente el cual necesitaba "montar" el *backend* para su tienda de manufactura y venta de productos dentro del ámbito del tenis.
> 
> Para ello, se nos propocionaba un enunciado del cual nosotros debíamos extraer, utiliazndo un diagrama de clases, las entidades que se necesitaban para la creación de la base de datos, y también sus relacones.
>
> Una vez con los datos y las relaciones presentes, se nos pedía implementar dos CRUD utilizando diferentes tecnologías:
>
>> **Exposed:** Una librería de Kotlin que nos permite interactuar con una base de datos de forma sencilla. Mediante el uso de entidades y anotaciones, sumado a la implementación de elementos como los *"DAO"* podemos crear un flujo de información dentro de la base de datos, la cual nos ayuda a poder realizar las operaciones CRUD y respetar las restricciones sin la necesidad de crear las mismas de forma manual como se haría con una base JDBC.
>
>> **Hibernate JPA:** Esta tecnología nos permite realizar una implementación de la tecnología JPA, la cual nos permite crear entidades y relaciones entre ellas, y a partir de estas, crear una base de datos que respete las restricciones de las entidades y relaciones. JPA tal y como nosotros la hemos utilizado, ofrece una serie de anotaciones que convierten el programa en una aplicación que genera tablas  y relaciones de forma automática, y en la que nosotros solo debemos preocuparnos de guiar correctamente el flujo de información con las anotaciones y la implementación de los operaciones CRUD.

<br>
<hr>
<br>

### Test
> Para finalizar, hemos tenido que realizar el control de calidad de controladores y repositorios para ambos proyectos, utilizando herramientas como **JUnit** y **Mockito**. Un total de más de 130 test que de forma correcta, demuestran el buen funionamiento de los proyectos.

<br>
<hr>
<br>