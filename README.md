[![versionjava](https://img.shields.io/badge/jdk-8,_9,_11-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)
[![versionvuejs](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/jonashackt/spring-boot-vuejs/master/frontend/package.json&query=$.dependencies.vue&label=vue&logo=vue.js)](https://vuejs.org/)
[![versionnodejs](https://img.shields.io/badge/dynamic/xml?color=brightgreen&url=https://raw.githubusercontent.com/jonashackt/spring-boot-vuejs/master/frontend/pom.xml&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27build%27%5D%2F%2A%5Blocal-name%28%29%3D%27plugins%27%5D%2F%2A%5Blocal-name%28%29%3D%27plugin%27%5D%2F%2A%5Blocal-name%28%29%3D%27executions%27%5D%2F%2A%5Blocal-name%28%29%3D%27execution%27%5D%2F%2A%5Blocal-name%28%29%3D%27configuration%27%5D%2F%2A%5Blocal-name%28%29%3D%27nodeVersion%27%5D&label=nodejs&logo=node.js)](https://nodejs.org/en/)
[![versionwebpack](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/jonashackt/spring-boot-vuejs/master/frontend/package-lock.json&query=$.dependencies.webpack.version&label=webpack&logo=webpack)](https://webpack.js.org/)
[![versionaxios](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/jonashackt/spring-boot-vuejs/master/frontend/package.json&query=$.dependencies.axios&label=axios)](https://github.com/axios/axios)
# Spring Company Meeting Agenda

A sample application to reserve shared meeting resources, such as rooms and presentation equipment, in a company. A shared agenda is shown to every user, allowing resource allocation. To make an appointment, an employee needs to log in and use the calendar to make his appointment, if the resource hasn't been already allocated for the time slot, a reserve is successfully made. Only the owner or system admins can edit the appointment.

---

The application uses a [Java Spring Framework](https://spring.io/) backend RESTful API, 
with a [PostgreSQL](https://www.postgresql.org/) database, along with a [VueJS](https://vuejs.org) frontend, with [Vuetify](https://vuetifyjs.com) UI components.

- The schema can be found in the __schema.sql__ file found in the resources directory;
- The API endpoints are all in the __.yaml__ file;

---