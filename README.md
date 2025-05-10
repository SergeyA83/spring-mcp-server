# Spring MCP Server Example

### Build from Source

```bash
#Building the application from the root directory of the project

./gradlew clean build

#Building the Docker image

docker build --tag=spring-mcp-server:latest .

#Running in Docker

docker run -p 8080:8080 spring-mcp-server:latest
```

