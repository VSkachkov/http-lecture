package repository;

import converter.AccountConverter;
import dto.AccountDto;
import dto.ResultDto;
import exception.ApplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepository {
    public static Map<Integer, String> accounts;
    static {
        accounts = new HashMap<>();
        accounts.put(1, "Account 1");
        accounts.put(2, "Account 2");
    }

    public List<AccountDto> getAll() {
        return accounts.entrySet().stream().map(entry -> AccountConverter.convert(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public AccountDto getById(final Integer id) {
        return AccountConverter.convert(id, accounts.get(id));
    }

    public void create(final Integer id, final String accountName) {
        if (accounts.get(id) == null) {
            accounts.put(id, accountName);
        }
        else {
            throw new ApplicationException(400, "Account exists");
        }
    }
}
