FROM eclipse-temurin:21-jdk

RUN useradd -r -u 1001 -m -d /home/appuser appuser \
    && mkdir -p /opt/home-budget \
    && chown -R appuser:appuser /opt/home-budget

COPY --chown=appuser:appuser ./target/*.jar /opt/home-budget/home-budget.jar

USER appuser
EXPOSE 8080
WORKDIR /opt/home-budget
ENTRYPOINT ["java", "-jar", "/opt/home-budget/home-budget.jar"]