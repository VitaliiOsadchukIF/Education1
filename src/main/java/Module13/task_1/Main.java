package Module13.task_1;

public class Main {

    public static void main(String[] args) {
        System.out.println(UserCrudApp.createUser());
        System.out.println(UserCrudApp.updateUser(1));
        System.out.println(UserCrudApp.deleteUser(2));
        System.out.println(UserCrudApp.getUsers());
        System.out.println(UserCrudApp.getUserByTd(4));
        System.out.println(UserCrudApp.getUserByUsername("Samantha"));


    }
}
