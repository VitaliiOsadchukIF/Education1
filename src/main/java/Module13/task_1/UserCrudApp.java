package Module13.task_1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class UserCrudApp {


    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient();

    // Метод для створення нового користувача
    public static UserEntity createUser() {
        try {

        // Створення JSON-тіла запиту з даними користувача
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"name\":\"Vitaliy Osadchuk\",\"username\":\"radistj\",\"email\":\"radistj@dmail.com\"}");

        // Створення POST-запиту з JSON-тілом і URL
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        // Відправлення запиту й отримання відповіді
        Response response = null;

            response = client.newCall(request).execute();


        if (response.isSuccessful()) {
            // Якщо відповідь успішна, повертаємо створений об'єкт UserEntity

                return getUserEntity(response.body().string());
            }
        }catch (IOException e) {
            throw new RuntimeException("Something went wrong", e);
        }

        return null;
    }

    // Метод для оновлення користувача за його id
    public static UserEntity updateUser(int id){
        try {
            // Створюємо JSON-тіло запиту з оновленими даними користувача
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    "{\"id\":3,\"name\":\"Spider Man\",\"username\":\"spiderman\",\"email\":\"Spiderman@gmail.com\"}");

            // Створюємо PUT-запит з JSON-тілом та URL
            Request request = new Request.Builder()
                    .url(BASE_URL + "/" + id)
                    .put(requestBody)
                    .build();

            // Відправляємо запит та отримуємо відповідь
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Якщо відповідь успішна, повертаємо оновлений об'єкт UserEntity
                return getUserEntity(response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод для видалення користувача за його id
    public static boolean deleteUser(int id) {

        try {

            // Створюємо DELETE-запит з URL
            Request request = new Request.Builder()
                    .url(BASE_URL + "/" + id)
                    .delete()
                    .build();

            // Відправляємо запит та отримуємо відповідь
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Метод для отримання списку користувачів
    public static List<UserEntity> getUsers() {

        try {
            // Створюємо GET-запит з URL
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .get()
                    .build();

            // Відправляємо запит та отримуємо відповідь
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Якщо відповідь успішна, розбираємо JSON-тіло в список об'єктів UserEntity
                String responseBody = response.body().string();
                return gson.fromJson(responseBody, new TypeToken<List<UserEntity>>() {
                }.getType());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Метод для отримання користувача за його id
    public static Optional<UserEntity> getUserByTd(int id) {
        try {
            // Створюємо GET-запит з URL
            Request request = new Request.Builder()
                    .url(BASE_URL + "/" + id)
                    .get()
                    .build();

            // Відправляємо запит та отримуємо відповідь
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Якщо відповідь успішна, розбираємо JSON-тіло в об'єкт UserEntity та повертаємо його, упакованого в Optional
                String responseBody = response.body().string();
                UserEntity user = gson.fromJson(responseBody, UserEntity.class);
                return Optional.ofNullable(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Метод для отримання користувача за його username
    public static Optional<UserEntity> getUserByUsername(String username) {
        try {
            // Побудова URL з параметром запиту username
            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
            // Додаємо параметр запиту зі значенням username
            urlBuilder.addQueryParameter("username", username);

            // Створення GET-запиту з побудованим URL
            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .get()
                    .build();

            // Відправлення запиту й отримання відповіді
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Якщо відповідь успішна і список користувачів не порожній, повертаємо першого користувача, упакованого в Optional
                String responseBody = response.body().string();
                // Розпаковування JSON-тіла в список об'єктів UserEntity за допомогою бібліотеки Gson
                List<UserEntity> users = gson.fromJson(responseBody, new TypeToken<List<UserEntity>>() {}.getType());
                // Перевіряємо, чи список користувачів не порожній
                if (!users.isEmpty()) {
                    // Шукайте користувача зі списку, чий username співпадає з введеним значенням
                    for (UserEntity user : users) {
                        if (user.getUsername().equals(username)) {
                            return Optional.of(user);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

// Приватний допоміжний метод для розпакування JSON-тіла та створення об'єкта UserEntity
    private static UserEntity getUserEntity(String responseBody) {
        return gson.fromJson(responseBody, UserEntity.class);
    }


}
