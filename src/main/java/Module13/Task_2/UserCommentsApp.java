package Module13.Task_2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserCommentsApp {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com"; //https://jsonplaceholder.typicode.com/users/1/posts
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        int userId = 1; // ID користувача
        int postId = getLatestPostId(userId); // Отримуємо ID останнього поста користувача

        List<Comment> comments = getPostComments(postId); // Отримуємо всі коментарі до останнього поста
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json"; // Формуємо назву файлу
        writeCommentsToFile(comments, fileName); // Записуємо коментарі у файл


    }

    // Метод для отримання ID останнього поста користувача
    private static int getLatestPostId(int userId) {
        try {
            String url = BASE_URL + "/users/" + userId + "/posts";

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                List<Post> posts = gson.fromJson(responseBody, new TypeToken<List<Post>>() {
                }.getType());

                if (!posts.isEmpty()) {
                    // Отримуємо останній пост за його ID
                    return posts.get(posts.size() - 1).getId();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        throw new RuntimeException("Unable to get latest post ID");
    }


    // Метод для отримання всіх коментарів до поста
    private static List<Comment> getPostComments(int postId) {

        try {
            String url = BASE_URL + "/posts/" + postId + "/comments";

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return gson.fromJson(responseBody,new TypeToken<List<Comment>>() {} .getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Unable to get post comments");

    }

    // Метод для запису коментарів у файл
    private static void writeCommentsToFile(List<Comment> comments, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            gson.toJson(comments, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
