
package com.example.filmatic.Domain;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ListFilm {

    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public List<Datum> getData() {
        return data;
    }
    public List<Datum> getSpecificMovies(List<Integer> specificIds) {
        List<Datum> result = new ArrayList<>();
        for (Datum datum : data) {
            if (specificIds.contains(datum.getId())) {
                result.add(datum);
            }
        }
        return result;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
