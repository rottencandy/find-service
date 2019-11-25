package com.redhat.cajun.navy.finding.service;

import com.redhat.cajun.navy.finding.client.IncidentRestClient;
import com.redhat.cajun.navy.finding.model.Victim;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FindService {

    private static final Logger logger = LoggerFactory.getLogger(FindService.class);

    @Inject
    @RestClient
    IncidentRestClient incidentRestClient;

    VictimCodec victimCodec = new VictimCodec();

    public Response getVictimByName(String name) {

        List<Victim> victimList = null;
        try {
            victimList = incidentRestClient.getByName(name);
            //JsonArray victimsArray = new JsonArray(victimList.stream().map(this::toJsonObject).collect(Collectors.toList()));
            //JsonObject jsonObject = new JsonObject().put("victims", victimsArray);


            return Response.ok(victimList).build();
        } catch (Exception ex) {
            logger.info("Unable to connect to Incident Service. ", ex);
            return Response.ok("Unable to connect to Incident Service.").build();
        }
    }

    private JsonObject toJsonObject(Victim victim) {
        return victimCodec.toJsonObject(victim);
    }

}
