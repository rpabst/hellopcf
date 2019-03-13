package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    //List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
    Map<Long, TimeEntry> timeEntries  = new HashMap<Long, TimeEntry>();

    private long current = 0L;

    public TimeEntry create(TimeEntry timeEntry) {

        if (timeEntry.getId() == - 1L)
        {
            current = current + 1;
            timeEntry.setId(current);
        }
        //System.out.println("x" + current);
        this.timeEntries.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long timeEntryId) {
        return timeEntries.get(timeEntryId);

    }

    public List<TimeEntry> list() {
        Collection coll = timeEntries.values();
         return new ArrayList(coll);
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
       if (timeEntries.containsKey(id)){
            timeEntry.setId(id);
            timeEntries.put(id, timeEntry );
           return timeEntry;
        }

       return null;

    }

    public TimeEntry delete(long id) {
        TimeEntry timeEntry = timeEntries.get(id);
        if (timeEntry != null){
            timeEntries.remove(id,timeEntry);
        }
        return null;
    }

}
