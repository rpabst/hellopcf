package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTimeEntryRepository() {

    }


    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours)" +
                    "VALUES (?,?,?,?)", RETURN_GENERATED_KEYS);

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);


        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {

       return jdbcTemplate.query("SELECT * FROM time_entries WHERE id = ?", new Object[]{timeEntryId},  extractor);

    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("SELECT * FROM time_entries", new Object[]{}, mapper);
    }

    @Override
    public TimeEntry update(long eq, TimeEntry timeEntry) {

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ?" +
                    " WHERE id = ?");

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            statement.setLong(5, eq);

            return statement;
        });

        return find(eq);
    }

    @Override
    public TimeEntry delete(long timeEntryId) {

        TimeEntry timeEntry = find(timeEntryId);
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = " + timeEntryId);

        return new TimeEntry();
    }


   private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );


    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}



