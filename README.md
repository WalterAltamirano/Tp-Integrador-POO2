# Tp-Integrador-POO2
!!Incluir esto en un Archivo PDF 👇🏼!!

TRABAJO FINAL POO2

Descripcion:
El repositorio que vamos a usar para desarrollar el Tp final de la materia Programacion con Objetos 2

Integrantes:

Walter Gabriel Altamirano // Comision 2

Ian Bravo //  Comision 1

Matias Maza Vega // Comision 1

Emails:
altamiranow4@gmail.com
ian_bravo014@hotmail.com
mjmazavega@gmail.com

Decisiones de Diseño y detalles de implementacion:

* No se puede comprar horas de Estacionamiento en el punto de venta que superen la hora limite
* Para notificar a los organismos interesados en los cambios del SEM, este se pasa a si mismo al no saber los detalles especificos que desean conocer
* Se determino que el estado inicial de EstadoApp seria EnAuto
* El modo automatico no puede funcionar si la estrategia de EstadoGPS no es encendido (se imprime en consola los respectivos mensajes cuando se intentan usar 2 estrategias incompatibles)
* Las notificaciones de inicio o finalizacion de estacionamiento de ambos modos (automatico o manual) se representaron imprimiendo la informacion en consola

Patrones utilizados Y los roles segun el libro "Patrones de Diseño Gamma et. al.":

* Patron Observer
- Sujeto: no se implemento una interfaz con estas funcionalidades ya que solo habia un elemento que calificara como Sujeto Concreto
- Sujeto Concreto: SEM
- Observador: Interfaz INotificar
- Observadores concretos: organismos interesados que al momento de creacion se desconocen pero que implementaran la interfaz correspondiente

* Patron Estate
- Contexto: AplicacionSEM
- Estado: EstadoApp
- Subclases concretas de Estado: Caminando y EnAuto

* Patron Strategy 1
- Contexto: AplicacionSEM
- Estrategia: Modo
- Estrategias concretas: ModoAutomatico y ModoManual

* Patron Strategy 2
- Contexto: AplicacionSEM
- Estrategia: EstadoGPS
- Estrategias concretas: Apagado y Encendido

* Template Method
Se formo una estructura de Template Method en las clases del Strategy 1, especificamente en los mensajes inicioDeEstacionamiento() y finDeEstacionamiento()


