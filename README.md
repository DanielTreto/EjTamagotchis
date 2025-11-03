# EjTamagotchis

Descripción
-----------
Pequeño ejercicio en Java que implementa un Tamagotchi (mascota virtual) y un Cuidador (clase que interactúa con la mascota). El proyecto está organizado como Maven y contiene el código fuente en `src/main` y (si procede) tests en `src/test`.

Estructura del repositorio
--------------------------
- .classpath
- .project
- .settings/ (configuración del IDE)
- pom.xml (definición de Maven)
- src/
  - src/main (código fuente Java)
  - src/test (pruebas)
- target/ (artefactos generados por la compilación)

Requisitos
----------
- Java JDK 11+ (o la versión indicada en `pom.xml`)
- Maven 3.6+

Cómo descargar / clonar el proyecto
----------------------------------
Puedes obtener el código de varias formas; elige la que prefieras.

1) Clonar con Git (HTTPS)
```bash
git clone https://github.com/DanielTreto/EjTamagotchis.git
cd EjTamagotchis
```

2) Clonar con Git (SSH, si tienes llave configurada)
```bash
git clone git@github.com:DanielTreto/EjTamagotchis.git
cd EjTamagotchis
```

3) Usando GitHub CLI
```bash
gh repo clone DanielTreto/EjTamagotchis
cd EjTamagotchis
```

4) Descargar ZIP desde la interfaz web
- Ve a: https://github.com/DanielTreto/EjTamagotchis
- Clic en "Code" → "Download ZIP", descomprime y entra en la carpeta.

5) Clonar y cambiar a otra rama (ejemplo)
```bash
git clone https://github.com/DanielTreto/EjTamagotchis.git
cd EjTamagotchis
git checkout nombre-de-rama
```

Compilar y ejecutar
-------------------
1. Compilar y empaquetar:
```bash
mvn clean package
```

2. Ejecutar pruebas:
```bash
mvn test
```

3. Ejecutar la aplicación:
- Desde Maven (reemplaza el paquete si procede; si la clase Cuidador está en el paquete por defecto, usa solo el nombre de la clase):
```bash
mvn -Dexec.mainClass="com.tu.paquete.Cuidador" exec:java
```
- O ejecutar directamente la clase compilada:
```bash
java -cp target/classes com.tu.paquete.Cuidador
```
- Si usas un IDE (Eclipse, IntelliJ), ejecuta la clase `Cuidador` con la configuración de ejecución normal.

Descripción de las clases
-------------------------

- Tamagotchi
  - Responsabilidad:
    Representa el modelo de la mascota virtual, con sus atributos y comportamiento (alimentarse, jugar, dormir, envejecer/pasar tiempo).
  - Atributos principales (comunes a este tipo de implementación):
    - hambre (int) — nivel de hambre (mayor = más hambriento)
    - energia (int) — nivel de energía
    - diversion (int) — nivel de felicidad/diversión
    - edad (int) — edad o tiempo de vida transcurrido
    - nombre (String) — nombre de la mascota (si aplica)
  - Métodos públicos importantes (firma orientativa):
    - void alimentar() — reduce el hambre, puede incrementar energía o diversión según implementación.
    - void jugar() — aumenta diversión, reduce energía y puede aumentar hambre.
    - void dormir() — restaura energía, puede avanzar tiempo.
    - void pasarTiempo() — simula paso del tiempo: incrementa hambre, reduce energía, etc.
    - String obtenerEstado() / toString() — devuelve una descripción del estado actual.
    - boolean estaViva() — indica si la mascota sigue con vida (si la lógica lo contempla).
  - Notas:
    - Asegúrate de que los niveles se mantengan dentro de límites (p. ej. 0..100).
    - Añade validaciones para no reducir atributos por debajo de 0 ni sobrepasar el máximo.

- Cuidador
  - Responsabilidad:
    Clase encargada de interactuar con el usuario y con una instancia de Tamagotchi. Suele implementar un bucle de interacción (CLI) que permite elegir acciones: alimentar, jugar, hacer dormir, mostrar estado, salir.
  - Métodos públicos principales:
    - public static void main(String[] args) — punto de entrada; inicializa el Tamagotchi y gestiona la interacción con el usuario.
    - void alimentarMascota() — invoca Tamagotchi.alimentar() y muestra el resultado.
    - void jugarConMascota() — invoca Tamagotchi.jugar().
    - void hacerDormirMascota() — invoca Tamagotchi.dormir().
    - void mostrarEstado() — muestra los atributos actuales del Tamagotchi.
  - Notas:
    - Si la interacción es por consola, utiliza Scanner para leer las opciones del usuario.
    - Considera persistencia simple (serialización o fichero) si quieres guardar el estado entre ejecuciones (no incluida por defecto).

Ejemplo de uso rápido
---------------------
- Ejecuta la clase Cuidador como se indica arriba. La aplicación debe iniciar el bucle de interacción y permitir acciones como:
  - 1) Alimentar
  - 2) Jugar
  - 3) Dormir
  - 4) Mostrar estado
  - 5) Salir
