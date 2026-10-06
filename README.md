# 👑 Visualizador N-Reinas con Backtracking

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-green?style=flat-square&logo=thymeleaf)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

Aplicación web interactiva y API REST desarrollada en **Java 17+** y **Spring Boot 3** que resuelve el clásico **Problema de las N-Reinas** utilizando un algoritmo de **Backtracking (Búsqueda en Profundidad con Poda)**.

El proyecto incluye una interfaz web dinámica construida con **Thymeleaf**, tableros visuales interactivos, cálculo de métricas de rendimiento en tiempo real y una API REST JSON.

---

## 📋 Tabla de Contenidos

- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Fundamento Algorítmico (Backtracking)](#-fundamento-algorítmico-backtracking)
    - [Complejidad Algorítmica](#complejidad-algorítmica)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución Local](#-instalación-y-ejecución-local)
- [Documentación de la API REST](#-documentación-de-la-api-rest)
- [Demostración de la Interfaz Web](#-demostración-de-la-interfaz-web)
- [Contribuciones y Licencia](#-contribuciones-y-licencia)

---

## 🌟 Características Principales

- **Algoritmo de Backtracking Optimizado:** Implementación recursiva eficiente con conteo exacto de evaluaciones de nodos y retrocesos (*backtracks*).
- **Interfaz Gráfica Thymeleaf:** Renderizado dinámico de tableros de ajedrez según la cantidad de soluciones encontradas.
- **Métricas de Rendimiento:** Cálculo preciso en tiempo real del tiempo de ejecución en milisegundos ($ms$), total de iteraciones y ramas podadas.
- **API REST Stateless:** Endpoint optimizado para integración externa con respuestas en formato JSON.
- **Estructura Empresarial:** Arquitectura desacoplada basada en servicios (`@Service`), controladores (`@Controller`, `@RestController`) y DTOs inmutables usando `Java Records`.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework Principal:** Spring Boot 3.x
- **Motor de Plantillas:** Thymeleaf
- **Estilos:** CSS3 / Flexbox & Grid
- **Construcción y Dependencias:** Apache Maven
- **Control de Versiones:** Git & GitHub

---

## 📂 Estructura del Proyecto

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── example/
    │           └── backtracking/
    │               ├── BacktrackingApplication.java
    │               ├── controller/
    │               │   ├── NQueensWebController.java
    │               │   └── NQueensRestController.java
    │               ├── dto/
    │               │   ├── ExecutionMetricsDTO.java
    │               │   └── SolutionResponseDTO.java
    │               └── service/
    │                   └── BacktrackingService.java
    └── resources/
        ├── static/
        └── templates/
            └── queens.html