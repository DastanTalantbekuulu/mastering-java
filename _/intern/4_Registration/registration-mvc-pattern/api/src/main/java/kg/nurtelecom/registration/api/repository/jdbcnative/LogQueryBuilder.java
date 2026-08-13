package kg.nurtelecom.registration.api.repository.jdbcnative;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import kg.nurtelecom.registration.api.exception.BadRequestException;
import kg.nurtelecom.registration.api.util.NumberUtil;
import kg.nurtelecom.registration.api.util.datatime.DateMatcher;
import kg.nurtelecom.registration.common.payload.request.log.SimplePageRequest;

public class LogQueryBuilder {
    public final static String SELECT_COUNT = """
            SELECT
                COUNT(log.id)
            FROM public.log
                LEFT JOIN public.version ON log.id = version.log_id
                LEFT JOIN public.log_security ON log.id = log_security.log_id
                LEFT JOIN public.entity ON version.entity_id = entity.id
            """;
    public final static String SELECT_ALL = """
            SELECT
                COUNT(*) OVER() AS count,
                log.id,
                log.log_time,
                log.action,
                log.username,
                entity.name as entity,
                version.data_id,
                version.version,
                log_security.ip_address,
                log_security.user_agent,
                log_security.response_code
            FROM public.log
                LEFT JOIN public.version ON log.id = version.log_id
                LEFT JOIN public.log_security ON log.id = log_security.log_id
                LEFT JOIN public.entity ON version.entity_id = entity.id
            """;
    public final static String GROUP_BY = """
            GROUP BY
                log.id,
                log.log_time,
                log.action,
                log.username,
                entity.name,
                version.data_id,
                version.version,
                log_security.ip_address,
                log_security.user_agent,
                log_security.response_code
            """;
    public final static String PAGINATION = " LIMIT ? OFFSET ? ";
    public final static String SELECT_PAGE = SELECT_ALL + " ORDER BY log.id DESC " + PAGINATION;
    public final static String SELECT_BY_ENTITY_AND_ENTITY_ID = """
            SELECT
                COUNT(*) OVER() AS count,
                log.id,
                log.log_time,
                log.action,
                log.username,
                entity.name as entity,
                version.data_id,
                version.version
            FROM public.log
                LEFT JOIN public.version ON log.id = version.log_id
                LEFT JOIN public.entity ON version.entity_id = entity.id
            WHERE entity.name = ? AND version.data_id = ?
            """;
    public final static String SELECT_BY_ID = """
            SELECT COUNT(*) OVER () AS count,
                   log.id,
                   log.log_time,
                   log.action,
                   log.username,
                   entity.name as entity,
                   version.data_id,
                   version.version,
                   log_security.ip_address,
                   log_security.user_agent,
                   log_security.response_code
            FROM public.log
                     LEFT JOIN public.version ON log.id = version.log_id
                     LEFT JOIN public.log_security ON log.id = log_security.log_id
                     LEFT JOIN public.entity ON version.entity_id = entity.id
            WHERE log.id = ?
            GROUP BY log.id,
                     log.log_time,
                     log.action,
                     log.username,
                     entity.name,
                     version.data_id,
                     version.version,
                     log_security.ip_address,
                     log_security.user_agent,
                     log_security.response_code
            """;
    public final static String SELECT_CHANGES = """
            SELECT version.changes,
                   entity.clazz
            FROM public.log
                     INNER JOIN public.version ON log.id = version.log_id
                     inner join public.entity on version.entity_id = entity.id
            WHERE log.id = ?
            LIMIT 1
            """;
    public final static String SELECT_CHANGE_COUNT = """
            WITH _json AS (
                SELECT json_build_object('record_identifier', version.entity_id, 'logs', COUNT(version.id)) as js
                FROM public.version INNER JOIN public.entity ON version.entity_id = entity.id
                WHERE entity.name = ?
                GROUP BY version.entity_id
            )
            SELECT json_agg(js) FROM _json
            """;
    public static final Map<String, String> validColumns = Map.of(
            "identifier", "log.id",
            "datetime", "log.log_time",
            "user", "log.username",
            "event", "log.action",
            "record_name", "entity.name",
            "record_identifier", "version.data_id",
            "record_version", "version.version",
            "ip", "log_security.ip_address",
            "agent", "log_security.user_agent",
            "code", "log_security.response_code"
    );

    public static String buildQuery(SimplePageRequest request, String where) {
        return SELECT_ALL + where + GROUP_BY + " ORDER BY " + orderByColumn(request.getSortBy()) + " " + (request.isAsc() ? "ASC" : "DESC") + PAGINATION;
    }

    public static String orderByColumn(String column) {
        return validColumns.containsKey(column) ? validColumns.get(column) : validColumns.get("identifier");
    }

    public static String buildWhere(SimplePageRequest request) {
        if ((request.getFrom() != null && request.getTo() == null) || (request.getTo() != null && request.getFrom() == null)) {
            throw new BadRequestException("Поля from и to должны быть заданы вместе");
        }
        StringBuilder where = new StringBuilder();
        if (request.hasBetween()) {
            where.append(" log.log_time BETWEEN ? AND ? ");
        }
        if (request.hasFilters()) {
            Map<String, String> filters = request.getFilters();
            for (String column : filters.keySet()) {
                String filter = filters.get(column);
                if (filter != null && !filter.isBlank()) {
                    build(where, column);
                }
            }
        }
        if (request.hasType()) {
            if (request.getType().equals("version")) {
                if (!where.isEmpty()) where.append(" AND ");
                where.append(" version.id IS NOT NULL ");
            } else if (request.getType().equals("login")) {
                if (!where.isEmpty()) where.append(" AND ");
                where.append(" log_security.id IS NOT NULL ");
            } else {
                throw new BadRequestException("Непонятный тип");
            }
        }
        return !where.isEmpty() ? " WHERE " + where : "";
    }

    private static void build(StringBuilder where, String column) {
        if (!validColumns.containsKey(column)) {
            throw new BadRequestException("Неправильное значение column: " + column);
        }
        if (!where.isEmpty()) {
            where.append(" AND ");
        }
        if (column.equals("datetime")) {
            where.append(" DATE_TRUNC('second', log.log_time) = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') ");
        } else {
            where.append(validColumns.get(column)).append("  = ? ");
        }
    }

    public static int applyParams(PreparedStatement statement, SimplePageRequest request) throws SQLException {
        int index = 0;
        if (request.hasBetween()) {
            if (!DateMatcher.isValidString(request.getFrom())) {
                throw new BadRequestException("Фильтр from не соответствует шаблону yyyy-MM-dd HH:mm:ss");
            }
            if (!DateMatcher.isValidString(request.getTo())) {
                throw new BadRequestException("Фильтр to не соответствует шаблону yyyy-MM-dd HH:mm:ss");
            }
            Timestamp from = Timestamp.valueOf(request.getFrom());
            Timestamp to = Timestamp.valueOf(request.getTo());
            if (from.after(to)) {
                throw new BadRequestException("Поле 'from' не может быть позже поля 'to'");
            }
            statement.setTimestamp(++index, from);
            statement.setTimestamp(++index, to);
        }
        if (request.hasFilters()) {
            Map<String, String> filters = request.getFilters();
            for (String column : filters.keySet()) {
                String filter = filters.get(column);
                if (filter != null && !filter.isBlank()) {
                    index = apply(statement, column, filter, index);
                }
            }
        }
        return index;
    }

    private static int apply(PreparedStatement statement, String column, String filter, int index) throws SQLException {
        if (filter.isBlank()) {
            throw new BadRequestException("filter не может быть пустым");
        }
        if (column.equals("datetime")) {
            if (!DateMatcher.isValidString(filter)) {
                throw new BadRequestException("Фильтр datetime не соответствует шаблону yyyy-MM-dd HH:mm:ss");
            }
            statement.setString(++index, filter);
        } else if (NumberUtil.isInteger(filter)) {
            statement.setInt(++index, Integer.parseInt(filter));
        } else {
            statement.setString(++index, filter);
        }
        return index;
    }
}
