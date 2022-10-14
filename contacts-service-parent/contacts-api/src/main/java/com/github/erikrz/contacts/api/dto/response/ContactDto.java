package com.github.erikrz.contacts.api.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Schema(description = "Defines a metadata model for units movement.")
public class ContactDto {

    @Schema(description = "Id of the contact.", example = "1")
    Long id;

    @Schema(description = "Contact's first Name.", example = "John")
    String firstName;

    @Schema(description = "Contact's Last Name.", example = "Doe")
    String lastName;

    @Schema(description = "Contact's email address.", example = "john.doe@github.com")
    String email;

    @Schema(description = "Contact's phone number.", example = "123-456-1234")
    String phoneNumber;

    @Schema(description = "Instant when this contact was first created in the system.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Instant createdDate;

    @Schema(description = "Instant when this contact was last Modified in the system.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Instant lastModifiedDate;
}
