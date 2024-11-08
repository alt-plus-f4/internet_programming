package org.exercise4.students.valentin;

public class Main {
    public static void main(String[] args) {
        ReqResClient usersApi = new ReqResClient();

        try {
            usersApi.listUsers();
            usersApi.getUserById(1);
            usersApi.getUserById(244);
            usersApi.createUser("Anne krank", "Man");
            usersApi.updateUser(1, "Julius Oppenheimer", "Scientist");
            usersApi.deleteUser(2);

            usersApi.validateAndEncodeUrl("https://google.com");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}