package sunshine;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import sunshine.interfaces.api.service.SearchService;

@ExtendWith(MockitoExtension.class)
public class ApiTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	SearchService searchService;

	@Test
	void searchWeatherTest() {

		//given
		Map<String, Object> geocode = Map.of("results", List.of(Map.of("latitude", "37.5665", "longitude", "126.9780")));

		Map<String, Object> weather = Map.of("current", Map.of("temperature_2m", 12.3, "apparent_temperature", 10.0, "weather_code", 3, "wind_speed_10m", 5.2, "relative_humidity_2m", 70));


		when(restTemplate.getForObject(contains("geocoding-api"), eq(Map.class))).thenReturn(geocode);

		when(restTemplate.getForObject(contains("forecast"), eq(Map.class))).thenReturn(weather);

		//when
		Map<String, Object> result = searchService.searchWeather("Seoul");

		//then
		assertThat(result).isNotNull();
		assertThat(result.get("current")).isInstanceOf(Map.class);

		Map current = (Map) result.get("current");

		assertThat(current.get("temperature_2m")).isEqualTo(12.3);
		assertThat(current.get("apparent_temperature")).isEqualTo(10.0);
		assertThat(current.get("weather_code")).isEqualTo(3);
		assertThat(current.get("wind_speed_10m")).isEqualTo(5.2);
		assertThat(current.get("relative_humidity_2m")).isEqualTo(70);
	}

	@Test
	void getMatrixTest() {
		//given
		Map<String, Object> geocode = Map.of("results", List.of(Map.of("latitude", "51.50853", "longitude", "-0.12574")));

		when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(geocode);

		//when
		Map<String, Object> matrix = searchService.getMatrix("London");

		//then
		assertThat(matrix.get("latitude")).isEqualTo("51.50853");
		assertThat(matrix.get("longitude")).isEqualTo("-0.12574");


	}
}
