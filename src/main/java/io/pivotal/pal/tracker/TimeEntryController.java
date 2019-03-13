package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRep;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRep = timeEntryRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/time-entries", method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        System.out.println(timeEntryToCreate.toString());
        TimeEntry tEntry = timeEntryRep.create(timeEntryToCreate);
        System.out.println(tEntry.toString());
       return new ResponseEntity<TimeEntry>(tEntry, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {

        TimeEntry timeEntry = timeEntryRep.find(timeEntryId);
        if (timeEntry == null)
        {
            return new ResponseEntity<TimeEntry>(new TimeEntry(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/time-entries", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<TimeEntry>> list() {
        return  new ResponseEntity (timeEntryRep.list(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRep.update(timeEntryId, expected);
        if (timeEntry == null)
        {
            return new ResponseEntity<TimeEntry>(new TimeEntry(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRep.delete(timeEntryId);

        return new ResponseEntity<TimeEntry>(new TimeEntry(), HttpStatus.NO_CONTENT);

    }


}
