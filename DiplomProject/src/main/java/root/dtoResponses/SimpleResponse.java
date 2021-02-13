package root.dtoResponses;

import root.dto.ErrorsDto;

public class SimpleResponse {
    private boolean result;
    private ErrorsDto errors;

    public SimpleResponse(boolean result, ErrorsDto errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ErrorsDto getErrors() {
        return errors;
    }

    public void setErrors(ErrorsDto errors) {
        this.errors = errors;
    }
}
