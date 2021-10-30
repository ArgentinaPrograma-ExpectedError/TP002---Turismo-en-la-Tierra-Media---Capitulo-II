Argentina Programa
#YoProgramo 2021
@ExpectedError: Denis Andersen,
                Matias Binsak,
                Gonzalo Ceballos,
                Myriam Perez.

Trabajo Práctico (parte 2)

Este trabajo tiene como objetivo afianzar la práctica de bases de datos y su interacción con Java.

Turismo en la Tierra Media
1. Planteo
La secretaría de turismo de la Tierra Media ha decidido crear un sistema para promocionar el turismo en su territorio. Éste contará con la información de las distintas atracciones de toda la Tierra Media.

El sistema deberá ser capaz de sugerir visitas a partir de la ubicación de los visitantes y también de generar itinerarios a partir de la información de preferencias disponible en el perfil de los usuarios. Se espera que los usuarios puedan descargarse una aplicación móvil que les permita interactuar con el sistema y los vaya guiando en su recorrido.

Dado el alcance del sistema y la limitación de tiempo, varios equipos han sido contratados para el desarrollo de este sistema.

En el caso de su equipo, ya se dispone de la lógica para sugerir visitas e itinerarios. Sin embargo el equipo encargado del incremento anterior no lo realizó utilizando bases de datos sino archivos para recuperar y almacenar la información.

Su tarea es:

Crear la base de datos, que contenga tablas para Tipo de Atracciones, Atracciones, Promociones, Usuarios, e Itinerarios.
Deberán recuperar de la base de datos la información necesaria para comenzar el proceso de recomendaciones.
Una vez seleccionadas las atracciones y promociones que comprará cada usuario, se desea guardar dicha combinación en la base de datos y actualizar los cupos, tiempos y dinero disponibles (según el caso).

Nota: El sistema se piensa para utilizarse en una única fecha (en el futuro). Por lo tanto el cupo de las atracciones sólo se considera para esa fecha y no hay que renovarlo para otra ocasión.

Agregado funcional:

Si un usuario vuelve a ingresar, se le continuarán sugiriendo promociones y atracciones según el criterio inicial, pero respetando las nuevas restricciones (el tiempo que le queda, el dinero que le queda, y el cupo restante en la atracción).
No deberán sugerirse duplicados.
