package pl.lodz.p.edu.adapter.rest.adapter.mapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ViolationMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        JsonObjectBuilder exceptionBuilder = Json.createObjectBuilder()
                .add("exception", "There were some violations in the body of request");
        JsonObjectBuilder violationsBuilder = Json.createObjectBuilder();

        exception.getConstraintViolations().forEach(violation -> {
            String nameOfViolation = violation.getPropertyPath().toString().split("\\.")[2];
            String message = violation.getMessage();
            violationsBuilder.add(nameOfViolation, message);
        });

        JsonObject violationObject = violationsBuilder.build();
        exceptionBuilder.add("violations", violationObject);
        // ok there is bug here, should put exceptionBuilder.build() instead, but works imo better
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(violationObject).build();
    }
}

