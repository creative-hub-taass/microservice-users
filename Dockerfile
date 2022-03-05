FROM openjdk:13-alpine
# avremo come base dell'immagine che creeremo
 #per il back end  il JDK che gira su alpine
RUN addgroup -S spring && adduser -S spring -G spring
#aggiungo spring come gruppo e come utente singolo, dentro al
# container ci sarà l'utente spring che probabilmente ha delle permission per tirarsi giù delle librerie
VOLUME /tmp
#definisco la cartella /tmp, tutti i dati andranno su tmp
EXPOSE 8080
#espone la porta 8080, quindi con postman ad esempio
#i rest controller saranno contattabili allo stesso modo
ARG DEPENDENCY=target
#aggiungo come dipendenza il target, è come fosse il war su JEE ho dentro tutto
#ciò che mi serve per l'app Spring
COPY ${DEPENDENCY}/*.jar appbootrest.jar
# aggiungo appboot, tutto cià che c'è sotto target
# diventa un jar e si chiama appbotrest.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/appbootrest.jar"]
#l'entrypoint della mia applicazione dentro il container sarà /appbotrest.jar
#si parla di java, nel senso che faccio eseguire spring che è in lnguaggio java
