# REQUIREMENTS

This is a possible list of requirements for the application.

## Backend
* Resources
  * CRUD functionality
  * Authorization: only system admins should be able to modify this resource
* Appointment
  * CRUD functionality
  * Needs to check if resources haven't already been reserved before update or creation
  * Authorization: only owner of the appointment and admin should be able to modify this resource
* Admins
  * CRUD functionality for admins
  * ~~Dashboard with statistics of resource usage by employees~~
  * ~~Data export to Excel / CSV documents~~
* ~~Users / Employees~~
  * ~~Sign up and password recovery possibilities~~
  * ~~Account deactivation~~
* Authentication:
  * Login route
  * JWT setup
  * Roles:
    * Anonymous
    * User
    * Admin

---

## Frontend
* Views:
  * Login
  * Appointment Calendar
  * ~~Profile~~
  * ~~Appointment History~~
  * ~~Preferences~~
    * ~~Theme~~