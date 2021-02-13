package root.services;

import root.enums.PostStatus;

public class PostStatusChecker {
    public static void ok(String status) throws Exception {
        try {
            PostStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Parameter status is wrong:\n" + e.getMessage());
            throw new Exception(e);
        }
    }
}
