package project.manager.server.enums;

import java.time.LocalDate;

public final class Constant {
    private Constant() {}

    public static final LocalDate MYSQL_MIN_DATE = LocalDate.of(1000, 1, 1);
    public static final Integer REVIEW_POINT = 1;
    public static final Integer BUILDING_POST_POINT = 15;

}
