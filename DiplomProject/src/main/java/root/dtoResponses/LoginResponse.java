package root.dtoResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import root.dto.UserDto;

public class LoginResponse {
    private boolean result;
    @JsonProperty("user")
    private UserDto userDto;

    public LoginResponse(boolean result, UserDto userDto) {
        this.result = result;
        this.userDto = userDto;
    }

    public LoginResponse(boolean result) {
        this.result = result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
