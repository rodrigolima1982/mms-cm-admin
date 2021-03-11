# mms-cm-admin
Admin application for the MMS Campaign Manager platform

Setup before running the application
---

1. Connect to the postgres database and create the user:
   ```sql
   create role mms_admin with password 'abc123' login;
   ```
1. Still connected on the postgres database, create the database:
   ```sql
   create database "mms_cm_admin" owner mms_admin encoding 'UTF8';
   ```
1. Execute following script
   ```sql
   INSERT INTO public.roles (id,"name") VALUES (1,'ROLE_ADMIN'), (2,'ROLE_MODERATOR'), (3,'ROLE_USER');
   ```

How to start the application
---

1. Run `mvn clean package` to build your application
1. Start application with `java -jar target/mms-sender-app-0.0.1-SNAPSHOT.jar`

