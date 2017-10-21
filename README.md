# Web Crawler

Basic system to track websites when their content changes.
Useful for tracking when tickets are on sale.

## Setting up a project:
- You need MySQL server to which you can connect
- After cloning the repo you need to provide the environment specific configuration:
  - `cp src/main/resources/config/application.properties.example src/main/resources/config/application.properties`
  - in `src/main/resources/config/application.properties` provide values that are relevant to your environment