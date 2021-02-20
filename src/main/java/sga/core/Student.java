package sga.core;

import com.google.gson.Gson;

public record Student(Integer id, String name, String surname) {

    public String fullName() {
        return name + surname;
    }

    /**
     * Returns a JSON object that can be used by MongoDB.
     *
     * @return      JSON object generated from POJO
     */
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Student.class);
    }
}