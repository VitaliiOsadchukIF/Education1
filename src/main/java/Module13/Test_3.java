package Module13; //Test_3

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class Test_3 {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        int userId = 1; // Ідентифікатор користувача

        List<Task> openTasks = getOpenTasks(userId); // Отримуємо відкриті задачі для користувача

        System.out.println("Open tasks for user " + userId + ":");
        for (Task task : openTasks) {
            System.out.println("Task ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("-----------------------");
        }
    }

    // Метод для отримання відкритих задач для користувача
    private static List<Task> getOpenTasks(int userId) {
        try {
            String url = BASE_URL + "/users/" + userId + "/todos";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                String responseBody = response.body().string();
                List<Task> tasks = gson.fromJson(responseBody, new TypeToken<List<Task>>() {}.getType());

                // Фільтруємо задачі, залишаючи тільки відкриті (completed = false)
                tasks.removeIf(Task::isCompleted);

                return tasks;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Unable to get open tasks for user " + userId);
    }


    public class Task {
        private int userId;
        private int id;
        private String title;
        private boolean completed;

        // Конструктори, геттери та сеттери


        public Task(int userId, int id, String title, boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        // Перевіряє, чи завершена задача
        public boolean isCompleted() {
            return completed;
        }
    }

//    public class User {
//        private int id;
//        private String name;
//        private String username;
//        private String email;
//
//        // Конструктори, геттери та сеттери
//    }

}

