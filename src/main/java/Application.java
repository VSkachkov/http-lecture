import com.google.gson.Gson;
import constants.Constants;
import constants.ErrorConstants;
import dto.AccountDto;
import dto.ResultDto;
import exception.ApplicationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.AccountRepository;

import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final Gson gson = new Gson();
    private static final AccountRepository accountRepository = new AccountRepository();

    public static void main(final String[] args) {
        configSparkServer();
        configExceptions();
        configBrowserEndpoints();
        configAccountEndpoints();
    }

    private static void configBrowserEndpoints() {
        path("browser", () -> {
            before((request, response) -> response.type(Constants.PLAIN_TEXT));
            get("", (req, res) -> {
                // TODO define browser from request
                // TODO depending on the browser return different text
                return "This browser is not defined";
            });
        });
    }

    /**
     * Configures endpoints of Spark Server
     */
    private static void configAccountEndpoints() {
        path("/api", () -> {
            get("/accounts", (req, res) -> {
                res.status(HttpStatus.OK_200);
                res.type(Constants.APPLICATION_JSON);
                return gson.toJson(accountRepository.getAll());
            });
            get("/account/:id", (req, res) -> {
                res.status(HttpStatus.OK_200);
                res.type(Constants.APPLICATION_JSON);
                return gson.toJson(accountRepository.getById(Integer.valueOf(req.params(":id"))));
            });
            post("/account/create", (req, res) -> {
                final AccountDto dto = gson.fromJson(req.body(), AccountDto.class);
                accountRepository.create(dto.getId(), dto.getAccount());
                res.status(HttpStatus.CREATED_201);
                res.type(Constants.APPLICATION_JSON);
                return gson.toJson(ResultDto.builder().success(true).build());
            });
            //TODO add methods to delete and update accounts using HTTP DELETE AND PUT METHODS
        });
    }

    /**
     * Configures Spark server always to response with JSON and ignore additional slash
     */
    private static void configSparkServer() {
        before((req, res) -> {
            final String path = req.pathInfo();
            if (path.endsWith("/"))
                res.redirect(path.substring(0, path.length() - 1));
        });
    }

    /**
     * Configures Spark server with exceptions wrappers
     */
    private static void configExceptions() {
        exception(ApplicationException.class, (exception, request, response) -> {
            response.status(exception.getCode());
            response.type(Constants.APPLICATION_JSON);
            response.body(gson.toJson(ResultDto
                    .builder()
                    .success(false)
                    .message(exception.getMessage())
                    .build()));
        });
        //for any unexpected exception response with Http status 500
        exception(RuntimeException.class, (exception, request, response) -> {
            response.status(500);

            response.body(gson.toJson(ResultDto
                    .builder()
                    .success(false)
                    .message(ErrorConstants.INTERNAL_SERVER_ERROR_MSG)
                    .build()));
        });
    }
}