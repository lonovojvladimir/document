package ru.com.document.dict;

import org.springframework.test.context.jdbc.Sql;
import ru.com.document.BaseControllerTest;

@Sql(scripts = {
        "classpath:dict/dict_schema.sql",
        "classpath:dict/audit_dict_schema.sql",
        "classpath:dict/data_dict_prod.sql"
})
public abstract class BaseDictControllerTest extends BaseControllerTest {

    @Override
    protected String getApiUrl() {
        return "dict";
    }
}
