package com.github.erikrz.contacts.api.dto.request;

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
@Schema(description = "Defines a model to create Contacts.")
public class CreateContactDto {

    @Schema(description = "Contact's first Name.", example = "John")
    String firstName;

    @Schema(description = "Contact's Last Name.", example = "Doe")
    String lastName;

    @Schema(description = "Contact's email address.", example = "john.doe@github.com")
    String email;

    @Schema(description = "Contact's phone number.", example = "123-456-1234")
    String phoneNumber;

}
