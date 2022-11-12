package ru.com.document.normative;

import org.springframework.test.context.jdbc.Sql;
import ru.com.document.BaseControllerTest;

@Sql(scripts = {
        "classpath:normative/schema.sql",
        "classpath:normative/schema_audit.sql",
        "classpath:normative/data_prod.sql"
})
public abstract class BaseNormativeControllerTest extends BaseControllerTest {

    protected final String typeCode = "1";
    protected final String sectionCode = "01";
    protected final String activityCode = "01";
    protected final String rowCode = "010";
    protected final String columnCode = "010";
    protected final String normRate = "0.13";

    @Override
    protected String getApiUrl() {
        return "normative";
    }
}
