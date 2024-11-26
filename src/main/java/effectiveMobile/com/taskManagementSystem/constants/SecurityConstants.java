package effectiveMobile.com.taskManagementSystem.constants;

import java.util.List;

public interface SecurityConstants {

    List<String> RESOURCES_WHITE_LIST = List.of(
            "/",
            "/error",
            "/swagger-ui/**",
            "/v3/api-docs/**");

    List<String> TASKS_WHITE_LIST = List.of(
            "/tasks",
            "/tasks/{id}");

    List<String> COMMENTS_WHITE_LIST = List.of(
            "/comments",
            "/comments/{id}");

    List<String> TASKS_PERMISSION_LIST = List.of(
            "/tasks/add",
            "/tasks/update",
            "/tasks/delete");

    List<String> COMMENTS_PERMISSION_LIST = List.of(
            "/comments/add",
            "/comments/update",
            "/comments/delete");

    List<String> USERS_WHITE_LIST = List.of("/login");

    List<String> USERS_PERMISSION_LIST = List.of("/tasks/*");

    List<String> USERS_REST_WHITE_LIST = List.of("/users/auth");
}