package root.services;

import org.springframework.stereotype.Service;
import root.enums.ModerationStatus;

@Service
public class ModerationStatusChecker {

    public static void ok(String status) throws Exception {
        try{
            ModerationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e){
            System.out.println("Parameter status is wrong:\n" + e.getMessage());
            throw new Exception(e);
        }
    }
}
