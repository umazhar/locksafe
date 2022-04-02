package persistence;

import org.json.JSONObject;

//writeable interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}