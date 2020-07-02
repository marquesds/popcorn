package me.lucasmarques.popcorn.api.v1;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.actor.repository.impl.RelationalDatabaseActorRepository;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
import me.lucasmarques.popcorn.director.repository.impl.RelationalDatabaseDirectorRepository;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import me.lucasmarques.popcorn.movie.exception.InvalidBodyException;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;
import me.lucasmarques.popcorn.movie.repository.impl.RelationalDatabaseMovieRepository;
import me.lucasmarques.popcorn.movie.schema.MovieDeserializer;
import me.lucasmarques.popcorn.movie.schema.MovieSerializer;
import me.lucasmarques.popcorn.movie.service.MovieService;
import me.lucasmarques.popcorn.utils.TestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.*;
import spark.Spark;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestApi {

    private final SystemConfig config = SystemConfig.getInstance();

    private final String baseUrl = "http://localhost:4567/api/v1/movies";

    private MovieService getService() {
        ConnectionDriver connection = new ConnectionDriver();

        ActorRepository actorRepository = new RelationalDatabaseActorRepository(connection);
        DirectorRepository directorRepository = new RelationalDatabaseDirectorRepository(connection);
        MovieRepository movieRepository = new RelationalDatabaseMovieRepository(connection, directorRepository, actorRepository);

        return new MovieService(movieRepository, directorRepository, actorRepository);
    }

    @BeforeClass
    public static void beforeAll() {
        String[] arguments = new String[]{};
        Api.main(arguments);
    }

    @AfterClass
    public static void afterAll() {
        Spark.stop();
    }

    @Before
    public void before() {
        TestUtils.cleanDatabase();
    }

    @Test
    public void testCanListNotCensoredMovies() throws InvalidBodyException {
        String url = String.format("%s?censura=SEM_CENSURA", baseUrl);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());

            JsonElement json = JsonParser.parseString(result);
            JsonElement movieJson = json.getAsJsonArray().get(0);
            Movie movie = MovieDeserializer.deserializer(movieJson.toString());

            Assert.assertEquals(movie.getName(), "Lion King");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanListCensoredMovies() throws InvalidBodyException {
        String url = String.format("%s?censura=CENSURADO", baseUrl);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());

            JsonElement json = JsonParser.parseString(result);
            JsonElement movieJson = json.getAsJsonArray().get(0);
            Movie movie = MovieDeserializer.deserializer(movieJson.toString());

            Assert.assertEquals(movie.getName(), "Saw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotListMoviesWithoutCensorshipQuery() {
        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(baseUrl);
        request.addHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            Assert.assertEquals("HTTP/1.1 404 Not Found", response.getStatusLine().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanPersistNewMovie() throws UnsupportedEncodingException {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Christopher Nolan"));

        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG, directors, actors);

        String json = MovieSerializer.serializeNonTransient(movie);
        StringEntity entity = new StringEntity(json);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            Assert.assertEquals("HTTP/1.1 201 Created", response.getStatusLine().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotPersistSameMovie() throws UnsupportedEncodingException {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Christopher Nolan"));

        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG, directors, actors);

        MovieService service = getService();
        try {
            service.save(movie);
        } catch (Exception e) {
        }

        String json = MovieSerializer.serializeNonTransient(movie);
        StringEntity entity = new StringEntity(json);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            String result = EntityUtils.toString(response.getEntity());
            Assert.assertEquals("HTTP/1.1 400 Bad Request", response.getStatusLine().toString());
            Assert.assertEquals("{\"message\":\"The movie Interstellar is already persisted on database.\"}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotPersistMovieWithoutCast() throws UnsupportedEncodingException {
        List<Actor> actors = new ArrayList<>();

        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Christopher Nolan"));

        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG, directors, actors);

        String json = MovieSerializer.serializeNonTransient(movie);
        StringEntity entity = new StringEntity(json);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            String result = EntityUtils.toString(response.getEntity());
            Assert.assertEquals("HTTP/1.1 400 Bad Request", response.getStatusLine().toString());
            Assert.assertEquals("{\"message\":\"The movie Interstellar dont have a cast.\"}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotPersistMovieWithoutDirector() throws UnsupportedEncodingException {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        List<Director> directors = new ArrayList<>();

        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG, directors, actors);

        String json = MovieSerializer.serializeNonTransient(movie);
        StringEntity entity = new StringEntity(json);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            String result = EntityUtils.toString(response.getEntity());
            Assert.assertEquals("HTTP/1.1 400 Bad Request", response.getStatusLine().toString());
            Assert.assertEquals("{\"message\":\"The movie Interstellar dont have any director.\"}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotPersistMovieWithInvalidBody() throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity("{\"attribute\":\"Some attribute here.\"}");

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            String result = EntityUtils.toString(response.getEntity());
            Assert.assertEquals("HTTP/1.1 400 Bad Request", response.getStatusLine().toString());
            Assert.assertEquals("{\"message\":\"Invalid body: {\"attribute\":\"Some attribute here.\"}\"}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotPersistMovieWithInvalidCastLength() throws UnsupportedEncodingException {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));
        actors.add(new Actor("Matthew McConaughey Clone"));
        actors.add(new Actor("Anne Hathaway Clone"));
        actors.add(new Actor("Jessica Chastain Clone"));
        actors.add(new Actor("Casey Affleck Clone"));
        actors.add(new Actor("Wes Bentley Clone"));
        actors.add(new Actor("Lucas Marques"));

        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Christopher Nolan"));

        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG, directors, actors);

        String json = MovieSerializer.serializeNonTransient(movie);
        StringEntity entity = new StringEntity(json);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost(baseUrl);
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            response.setHeader("Content-Type", "application/json");
            String result = EntityUtils.toString(response.getEntity());
            Assert.assertEquals("HTTP/1.1 400 Bad Request", response.getStatusLine().toString());
            Assert.assertEquals("{\"message\":\"Invalid cast length (11). Max cast length: 10.\"}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
