package com.flight.capstonetester;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CapstoneTesterApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() throws IOException {
        final String uri = "http://api.aviationstack.com/v1/flights?access_key=adcb199ee23d5047d44c69bd13d23452";

        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JsonNode rootNode = mapper.readTree(result);
        JsonNode dataNode = rootNode.path("data");

        JsonParser parser = mapper.createParser(dataNode.toString());

        parser.nextToken();

        List<FlightInfo> flightInfos = new ArrayList<>();

        while(parser.nextToken() == JsonToken.START_OBJECT) {
            FlightInfo flightInfo = parser.readValueAs(FlightInfo.class);
            flightInfos.add(flightInfo);
        }

        System.out.println(flightInfos.get(0));

        /*int count = 0;
        for (FlightInfo flightInfo : flightInfos) {
            System.out.printf("Result Num: %d | %s\n", count, flightInfo);
            count++;
        }*/

        parser.close();
    }

    @Test
    void testDB() {
        /*String sql0 = "DELETE FROM DEREK_TEST";
        jdbcTemplate.update(sql0);

        String sql = "INSERT INTO DEREK_TEST VALUES ('TEST TEST TEST')";
        jdbcTemplate.execute(sql);*/

        String sql1 = "SELECT * FROM DEREK_TEST";
        System.out.println(jdbcTemplate.queryForObject(sql1, String.class));
    }

}
