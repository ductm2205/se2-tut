# SE2 Tutorials

This repository contains the source code for all Software Engineering 2 tutorials at school.

## Structure

- Each branch represents a separate tutorial, starting from `tut-05`.
- The main branch contains the latest updates from all other branches.

## Navigation

To access a specific tutorial:

```bash
git checkout tut-05  # Replace tut-05 with the desired tutorial branch
```

## Updates

The main branch is regularly updated to incorporate changes from all tutorial branches.

## Getting Started

To get started with this project:

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/se2_tutorials.git
   cd se2_tutorials
   ```

## Running the Application

### Using Docker

1. Create an environment variables file:

   ```bash
   cp .env.example .env
   ```

2. Edit the `.env` file with your database credentials:

   ```plaintext
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=your_database_name
   DB_USER=your_username
   DB_PASSWORD=your_password
   DB_ROOT_PASSWORD=your_root_password
   ```

3. Run with Docker Compose:

   ```bash
   docker-compose up
   ```

4. Access the application:
   - Main application: [http://localhost:8080](http://localhost:8080)
   - PHPMyAdmin: [http://localhost:8082](http://localhost:8082)

### Running Without Docker

If you prefer to run without Docker:

1. Ensure you have Java 17 and Maven installed.
2. Configure your MySQL database.
3. Run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```
