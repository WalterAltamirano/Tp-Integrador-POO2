# Tp-Integrador-POO2

TRABAJO FINAL POO2

Descripcion:
El repositorio que vamos a usar para desarrollar el Tp final de la materia Programacion con Objetos 2

UML: https://drive.google.com/file/d/1RYWpFKlDohyTxtWWlcnIHLlAzkGRPCsw/view?usp=sharing

Integrantes:

Walter Gabriel Altamirano // Comision 2

Ian Bravo //  Comision 1

Matias Maza Vega // Comision 1

Emails:
altamiranow4@gmail.com
ian_bravo014@hotmail.com
mjmazavega@gmail.com

Decisiones de Diseño y detalles de implementacion:

* No se puede comprar horas de Estacionamiento en el punto de venta que superen la hora
límite
* El punto de venta no permite comprar horas de estacionamiento antes de que comience la
respectiva franja horaria
* Si un usuario se estacionó dentro del horario gratuito y no se retiró una vez iniciada la
franja horaria, será su responsabilidad iniciar el estacionamiento de forma manual desde la
aplicación
* Para notificar a los organismos interesados en los cambios del SEM se optó por un
modelo de Listener con mensajes específicos para cada cambio
* Se determinó que el estado inicial de EstadoApp sería EnAuto
* Las notificaciones de inicio o finalización de estacionamiento de ambos modos (automático
o manual) se representaron imprimiendo la información en consola
*Al momento de manejar la Hora de inicio o de fin de un estacionamiento se decidió trabajar
solo con las horas y no con los minutos
*La excepción personalizada fue creada con la finalidad de cortar el flujo del programa
cuando una de las condiciones para iniciar o finalizar un estacionamiento no se cumple,
evitando así que se ejecuten mensajes no deseados


Patrones utilizados Y los roles segun el libro "Patrones de Diseño Gamma et. al.":

* Patron Observer
- Sujeto: no se implemento una interfaz con estas funcionalidades ya que solo habia un elemento que calificara como Sujeto Concreto
- Sujeto Concreto: SEM
- Observador: Interfaz SemListener
- Observadores concretos: organismos interesados que al momento de creacion se desconocen pero que implementaran la interfaz correspondiente

* Patron Estate
- Contexto: AplicacionSEM
- Estado: EstadoApp
- Estados concretos: Caminando y EnAuto

* Patron Strategy 1
- Contexto: AplicacionSEM
- Estrategia: Modo
- Estrategias concretas: ModoAutomatico y ModoManual




