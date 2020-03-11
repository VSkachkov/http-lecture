package converter;

import constants.ErrorConstants;
import dto.AccountDto;
import exception.ApplicationException;
import org.eclipse.jetty.http.HttpStatus;

/**
 * Class to convert entities to DTO
 */
public class AccountConverter {
    /**
     * Converts entity to DTO
     * @param id account ID
     * @param name account name
     * @return account DTO
     */
    public static AccountDto convert(final Integer id, final String name) {
        if (id == null || name == null) {
            throw new ApplicationException(HttpStatus.NOT_FOUND_404, ErrorConstants.REQUESTED_DATA_NOT_FOUND);
        }
        return AccountDto.builder()
                .id(id)
                .account(name)
                .build();
    }
}
