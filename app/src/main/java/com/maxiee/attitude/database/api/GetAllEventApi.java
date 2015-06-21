package com.maxiee.attitude.database.api;

import android.content.Context;
import android.database.Cursor;

import com.maxiee.attitude.database.tables.EventsTable;
import com.maxiee.attitude.model.Event;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by maxiee on 15-6-12.
 */
public class GetAllEventApi extends BaseDBApi {

    public GetAllEventApi(Context context) {
        super(context);
    }

    public ArrayList<Event> exec() throws JSONException{
        Cursor cursor = mDatabaseHelper.getReadableDatabase().query(
                EventsTable.NAME,
                new String[] {
                        EventsTable.ID,
                        EventsTable.EVENT,
                        EventsTable.TIMESTAMP
                },
                null,
                null, null, null, null
        );

        if (cursor.getCount() < 1) {
            return null;
        }

        cursor.moveToFirst();
        ArrayList<Event> eventList = new ArrayList<>();
        do {
            int id = cursor.getInt(
                    cursor.getColumnIndex(EventsTable.ID)
            );
            String event = cursor.getString(
                    cursor.getColumnIndex(EventsTable.EVENT)
            );

            long timestamp = cursor.getLong(
                    cursor.getColumnIndex(EventsTable.TIMESTAMP)
            );
            eventList.add(new Event(
                    id,
                    event,
                    timestamp
            ));

        } while (cursor.moveToNext());

        cursor.close();

        return eventList;
    }
}
