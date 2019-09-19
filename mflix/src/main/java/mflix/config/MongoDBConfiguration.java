package mflix.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.SslSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Configuration
@Service
public class MongoDBConfiguration {

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public MongoClient mongoClient(@Value("${spring.mongodb.uri}") String connectionString) {

    ConnectionString connString = new ConnectionString(connectionString);

    //TODO> Ticket: Handling Timeouts - configure the expected
    // WriteConcern `wtimeout` and `connectTimeoutMS` values
    final MongoClientSettings settings = MongoClientSettings
            .builder()
            .applicationName("mflix")
            .applyConnectionString(connString)
            .writeConcern(new WriteConcern("majority").withWTimeout(2500, TimeUnit.MILLISECONDS)) // wtimeoutMS
            .applyToClusterSettings(builder ->
                    builder
                            .applyConnectionString(connString)
                            .serverSelectionTimeout(5000, TimeUnit.MILLISECONDS) // mentioned on video
                            .build())
            .applyToConnectionPoolSettings(builder ->
                    builder
                            .applyConnectionString(connString)
                            .maxSize(50)  // maxPoolSize
                            .build())
            .applyToSocketSettings(builder ->
                    builder
                            .applyConnectionString(connString)
                            .connectTimeout(2000, TimeUnit.MILLISECONDS) // connectTimeoutMS
                            .build())
            .build();



/*    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString)).build();


    SslSettings sslSettings = settings.getSslSettings();
    ReadPreference readPreference = settings.getReadPreference();
    ReadConcern readConcern = settings.getReadConcern();
    WriteConcern writeConcern = settings.getWriteConcern();

    System.out.println("" + readPreference.toString() );
    System.out.println("" + readConcern.asDocument().toString());
    System.out.println("" + sslSettings.isEnabled());
    System.out.println("" + writeConcern.asDocument().toString());
    System.out.println("" + sslSettings.isInvalidHostNameAllowed());*/

    //return MongoClients.create(connectionString) ;
    return MongoClients.create(settings);
  }
}
