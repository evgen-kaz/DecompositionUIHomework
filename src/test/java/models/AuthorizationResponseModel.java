package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationResponseModel {
    @JsonProperty("created_date")
    String createdDate;

    String expires, isActive, password, token, userId, username;
}
