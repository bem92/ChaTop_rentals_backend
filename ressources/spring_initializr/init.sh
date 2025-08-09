curl https://start.spring.io/starter.zip \
  -d dependencies=web,security,data-jpa,mysql,lombok \
  -d type=maven-project \
  -d language=java \
  -d name=chatop-backend \
  -d groupId=com.chatop \
  -d artifactId=chatop-backend \
  -d packageName=com.chatop \
  -d javaVersion=17 \
  -o chatop-backend.zip

unzip -q chatop-backend.zip
rm chatop-backend.zip
