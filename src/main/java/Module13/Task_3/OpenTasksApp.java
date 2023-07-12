package Module13.Task_3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class OpenTasksApp {

    // Адреса базового URL для API
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        int userID = 1; // Ідентифікатор користувача

        List<Task> openTasks = getOpenTasks(userID); // Отримуємо відкриті задачі для користувача

        // Вивести список відкритих завдань
        System.out.println("Open tasks for user " + userID + ":");
        for (Task task : openTasks) {
            System.out.println("Task ID: " + task.getId());
            System.out.println("Task ID: " + task.getTitle());
            System.out.println("-----------------------");
        }
    }
    // Метод для отримання відкритих задач для користувача
    private static List<Task> getOpenTasks(int userId) {

        try {
            // Створити URL для запиту до API
            String url = BASE_URL + "/users/" + userId + "/todos";

            // Створюємо клієнта OkHttpClient для виконання запиту
            OkHttpClient client = new OkHttpClient();

            // Створюємо запит GET з URL-адресою
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            // Виконуємо запит та отримуємо відповідь
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                String responseBody = response.body().string();
                List<Task> tasks = gson.fromJson(responseBody, new TypeToken<List<Task>>() {
                }.getType());

                // Фільтруємо задачі, залишаючи тільки відкриті (completed = false)
                tasks.removeIf(task -> {
                    if (task.isCompleted()) return true;
                    else return false;
                });

                return tasks;
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
        throw new RuntimeException("Unable to get open tasks for user " + userId);
        
    }
}
