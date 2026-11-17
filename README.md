# 🏋️ Coach Gym — Sistema de Gestión y Control de Acceso

Sistema web desarrollado en **Spring Boot** para la administración integral de gimnasios. Permite gestionar socios con carnetización QR, facturación y renovaciones automáticas de membresías, control de acceso en tiempo real mediante escáner web, reservación de clases grupales con control de aforo y segregación de permisos por roles.

---

## 🚀 Características Principales

* **Seguridad y Control de Roles (`RolInterceptor`)**:
    * Autenticación basada en sesiones HTTP con dos perfiles: **Administrador (`ADMIN`)** y **Recepción (`RECEPCION`)**.
    * Restricción tanto visual en la barra de navegación como a nivel de servidor para proteger rutas financieras, configuración de tarifas/clases y eliminación de registros.
* **Dashboard Analítico en Tiempo Real**:
    * Indicadores clave del día: clientes activos, membresías por vencer ($\le 5$ días), membresías vencidas y asistencias permitidas.
    * Gráfica interactiva de estado de membresías (**Chart.js**) y bitácora en vivo de los últimos accesos en recepción.
* **Gestión de Socios y Carnetización QR**:
    * Asignación automática de código correlativo (`CG-0001`, `CG-0002`, etc.) y buscador rápido por nombre o código.
    * Cálculo automático de estado (`ACTIVO`, `POR_VENCER`, `VENCIDO`) y generación instantánea de código **QR** individual.
* **Facturación y Continuidad de Membresías**:
    * Registro de pagos vinculados a planes configurables y métodos de pago (Efectivo, Tarjeta, Transferencia).
    * **Lógica de renovación**: Si el socio renueva antes de su fecha de corte, los días se suman a partir de su vencimiento actual; si la membresía ya venció, el nuevo período inicia desde la fecha de cobro.
* **Control de Asistencia con Escáner QR**:
    * Lector integrado mediante cámara web (**html5-qrcode**) con alternativa de ingreso manual por código.
    * Validación instantánea de vigencia en puerta (`✅ Bienvenido` / `❌ Membresía vencida`) y registro histórico de todos los intentos de acceso.
* **Reservas de Clases Grupales**:
    * Agendamiento de socios en horarios programados validando membresía vigente, prevención de reservas duplicadas el mismo día y control de cupo máximo.
* **Panel Administrativo**:
    * Reporte acumulado de ingresos totales, gestión de planes tarifarios, apertura de clases/instructores y administración de cuentas de personal.

---

## 🛠️ Stack Tecnológico

* **Backend**: Java 21, Spring Boot (Web MVC, Spring Data JPA / Hibernate)
* **Frontend**: Thymeleaf, Bootstrap 5, Chart.js, QRCode.js, html5-qrcode
* **Base de Datos**: MySQL / MariaDB (XAMPP)
* **Gestor de Dependencias**: Maven (`mvnw` configurado con Maven 3.9.9)

---

## 📂 Arquitectura del Proyecto

```text
src/main/java/com/coachgym/coachgym/
├── config/          # DataInitializer (datos semilla), RolInterceptor y WebConfig
├── controller/      # Controladores MVC (Auth, Gym, Operacion, Admin)
├── model/           # Entidades JPA (Usuario, Plan, Cliente, Pago, Asistencia, Clase, Reserva)
├── repository/      # Interfaces Spring Data JPA
└── service/         # Lógica y reglas de negocio (Cliente, Pago, Asistencia, Reserva)

src/main/resources/
├── templates/       # Vistas HTML Thymeleaf (login, dashboard, clientes, pagos, asistencia, reservas, admin)
└── application.properties


⚙️ Instalación y Ejecución Local1. 
Requisitos previosJDK 21 instalado.XAMPP (o servidor MySQL/MariaDB local).
IDE compatible con Maven (IntelliJ IDEA, VS Code o Spring Tool Suite).
2. Configurar la base de datosInicia Apache y MySQL en el panel de control de XAMPP.Abre phpMyAdmin (http://localhost/phpmyadmin) y crea una base de datos vacía llamada:
SQLCREATE DATABASE coach_gym CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

3. Verificar application.propertiesAbre src/main/resources/application.properties y ajusta el puerto de MySQL (3306 por defecto en XAMPP, o 3307 si tienes otro puerto configurado) y la contraseña si aplica:Propertiesspring.application.name=coachgym

spring.datasource.url=jdbc:mysql://localhost:3306/coach_gym?useSSL=false&serverTimezone=America/El_Salvador&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

server.port=8080
server.servlet.session.tracking-modes=cookie
4. Ejecutar la aplicaciónAbre el proyecto en IntelliJ IDEA y espera a que Maven sincronice las dependencias.
Ejecuta la clase principal CoachgymApplication.java.Hibernate creará automáticamente las 7 tablas y cargará los datos iniciales.
Abre el navegador en: http://localhost:8080🔐 Credenciales de Prueba (Datos Iniciales)Al iniciar por primera vez, el sistema crea automáticamente dos usuarios, tres planes base (Mensual, Trimestral, Anual) y dos clases grupales:RolUsuarioContraseñaPermisosAdministradoradminadmin123Acceso total, ingresos, gestión de planes, clases, usuarios y eliminación de registros.Recepciónrecepcionrecep123Registro de clientes, cobros, escáner de asistencia y reservas de clases.