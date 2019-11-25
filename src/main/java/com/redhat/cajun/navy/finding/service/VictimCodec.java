package com.redhat.cajun.navy.finding.service;

import com.redhat.cajun.navy.finding.model.Victim;
import io.vertx.core.json.JsonObject;

public class VictimCodec {

    public JsonObject toJsonObject(Victim victim) {
        return new JsonObject().put("id", victim.getId())
                .put("lat", victim.getLat())
                .put("lon", victim.getLon())
                .put("medicalNeeded", victim.isMedicalNeeded())
                .put("numberOfPeople", victim.getNumberOfPeople())
                .put("victimName", victim.getVictimName())
                .put("victimPhoneNumber", victim.getVictimPhoneNumber())
                .put("timeStamp", victim.getTimestamp())
                .put("status", victim.getStatus());
    }

    public Victim fromJsonObject(JsonObject jsonObject) {
        return new Victim.Builder(jsonObject.getString("id"))
                .lat(jsonObject.getDouble("lat").toString())
                .lon(jsonObject.getDouble("lon").toString())
                .medicalNeeded(jsonObject.getBoolean("medicalNeeded"))
                .numberOfPeople(jsonObject.getInteger("numberOfPeople"))
                .victimName(jsonObject.getString("victimName"))
                .victimPhoneNumber(jsonObject.getString("victimPhoneNumber"))
                .timestamp(jsonObject.getLong("timeStamp"))
                .status(jsonObject.getString("status"))
                .build();
    }

}
