package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Class that incorporates all the application exceptions
 */
@Getter
@Builder
@AllArgsConstructor
public class ResultDto {
    boolean success;
    String message;
}
