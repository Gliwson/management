package pl.management.map.service.json;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.management.map.repository.DataRepo;
import pl.management.map.service.dto.ListJson;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ImportSheetsGoogleJson {

    private DataRepo dataRepo;
    @Value("${url.json}")
    public static String URL_JSON = "${JAVA_HOME}";

    public ImportSheetsGoogleJson(DataRepo dataRepo) {
        this.dataRepo = dataRepo;
    }

    public void getJson() {
        RestTemplate restTemplate = new RestTemplate();
        String stringJson = restTemplate.getForObject(URL_JSON, String.class);
        Gson gson = new Gson();
        List<ListJson> user = gson.fromJson(stringJson, (Type) ListJson.class);
        System.out.println(user);
    }

//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        String stringJson = restTemplate.getForObject(URL_JSON, String.class);
//        Gson gson = new Gson();
//        JsonArray gson1 = new JsonArray();
//
//
////        Type listType = new TypeToken<ArrayList<User>>(){}.getType();
//        ListUser user = gson.fromJson(stringJson, ListUser.class);
//        user.getUser().stream().forEach(System.out::println);
//    }
}
