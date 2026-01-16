# MapleStory Weekly Bossing Tracker

A RESTful API to track weekly bossing income in MapleStory. This application helps players manage and monitor their weekly boss crystal earnings across multiple characters.

## Features

- Track weekly boss completions and income
- Manage multiple characters
- Record boss crystal prices and rewards
- Calculate total weekly earnings
- RESTful API architecture for easy integration
- Docker support for simplified deployment

## Tech Stack

- **Language**: Java
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Architecture**: RESTful API

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (optional, for containerized deployment)
- Docker Compose (optional)

## Installation

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/mason-cong/Maplestory-Weekly-Bossing-Tracker.git
cd Maplestory-Weekly-Bossing-Tracker
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

### Docker Deployment

1. Build and run using Docker Compose:
```bash
docker-compose up -d
```

2. Or build the Docker image manually:
```bash
docker build -t maplestory-bossing-tracker .
docker run -p 8080:8080 maplestory-bossing-tracker
```

## API Usage

The API will be available at `http://localhost:8080` once running.

### Example Endpoints

*(Note: Update these endpoints based on your actual implementation)*

- `GET /api/characters/{userId}` - List all characters
- `POST /api/characters/{userId}` - Create a new character
- `GET /api/characters/{userId}/{characterId}/bosses` - List all available bosses
- `GET /api/characters/{userId}/{characterId}/weekly-meso` - Get weekly income for a specific character

## Configuration

Configuration files can be found in `src/main/resources/application.properties` use a .env for environment variables

Key configuration options:
- Server port
- Database connection settings
- Boss crystal base prices
- Weekly reset schedule

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/maplestory/
│   │       ├── controller/    # REST endpoints
│   │       ├── service/       # Business logic
│   │       ├── model/         # Entity classes
│   │       └── repository/    # Data access layer
│   └── resources/
│       └── application.properties
└── test/
```

## Roadmap

- [ ] Implement weekly reset automation
- [ ] Support for multiple MapleStory regions (GMS, KMS, etc.)


## Acknowledgments

- MapleStory is a trademark of Nexon
- Built for the MapleStory community to help track weekly progression

## Contact

Project Link: [https://github.com/mason-cong/Maplestory-Weekly-Bossing-Tracker](https://github.com/mason-cong/Maplestory-Weekly-Bossing-Tracker)

---

**Note**: This is a fan-made tool and is not affiliated with or endorsed by Nexon.
