package com.github.erikrz.contacts.service.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.erikrz.contacts.api.dto.request.CreateContactDto;
import com.github.erikrz.contacts.api.dto.response.ContactDto;
import com.github.erikrz.contacts.service.mapper.ContactMapper;
import com.github.erikrz.contacts.service.model.Contact;
import com.github.erikrz.contacts.service.repository.ContactsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests that verifies the ContactsService behavior.
 */
@ExtendWith(SpringExtension.class)
@Import(ContactsService.class)
class ContactsServiceTest {

    @MockBean
    private ContactsRepository contactsRepository;

    @Autowired
    private ContactsService contactsService;

    private final ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);;

    private final CreateContactDto createContact = CreateContactDto.builder()
            .firstName("Erik")
            .lastName("Rz")
            .email("erikrz@github.com")
            .phoneNumber("(123) 456 7890")
            .build();

    private final ContactDto savedContact = ContactDto.builder()
            .id(1L)
            .firstName("Erik")
            .lastName("Rz")
            .email("erikrz@github.com")
            .phoneNumber("(123) 456 7890")
            .build();

    private final CreateContactDto updateContact = CreateContactDto.builder()
            .firstName("Erik")
            .lastName("Rz")
            .email("erik.rz@github.com")
            .phoneNumber("(123) 456 7890")
            .build();

    private final ContactDto updatedContact = ContactDto.builder()
            .id(1L)
            .firstName("Erik")
            .lastName("Rz")
            .email("erik.rz@github.com")
            .phoneNumber("(123) 456 7890")
            .build();

    @Test
    void whenSaveContact_thenReturnsValidSavedContact() {
        when(contactsRepository.save(any(Contact.class))).thenReturn(contactMapper.toContact(savedContact));

        var result = contactsService.saveContact(createContact);

        assertThat(result).isNotNull().isEqualTo(savedContact);
        verify(contactsRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void whenGetContacts_thenReturnsValidListOfContacts() {
        when(contactsRepository.findAll()).thenReturn(List.of(contactMapper.toContact(savedContact)));

        var result = contactsService.getContacts();

        assertThat(result).isNotNull().hasSize(1).containsExactly(savedContact);
        verify(contactsRepository, times(1)).findAll();
    }

    @Test
    void whenUpdateInexistingContact_thenReturnsEmpty() {
        when(contactsRepository.findById(2L)).thenReturn(Optional.empty());

        var result = contactsService.updateContactById(2L, updateContact);

        assertThat(result).isEmpty();
        verify(contactsRepository, times(1)).findById(2L);
    }

    @Test
    void whenUpdateContact_thenReturnsUpdatedContact() {
        when(contactsRepository.findById(1L)).thenReturn(Optional.of(contactMapper.toContact(savedContact)));
        when(contactsRepository.save(contactMapper.toContact(updatedContact))).thenReturn(contactMapper.toContact(updatedContact));

        var result = contactsService.updateContactById(1L, updateContact);

        assertThat(result).isPresent();
        verify(contactsRepository, times(1)).findById(1L);
        verify(contactsRepository, times(1)).save(contactMapper.toContact(updatedContact));
    }

    @Test
    void whenDeleteUnexistingContact_thenReturnsEmpty() {
        when(contactsRepository.findById(2L)).thenReturn(Optional.empty());

        var result = contactsService.deleteContactById(2L);

        assertThat(result).isEmpty();
        verify(contactsRepository, times(1)).findById(2L);
        verify(contactsRepository, never()).deleteById(2L);
    }

    @Test
    void whenDeleteContact_thenReturnsDeletedContact() {
        when(contactsRepository.findById(1L)).thenReturn(Optional.of(contactMapper.toContact(savedContact)));

        var result = contactsService.deleteContactById(1L);

        assertThat(result).isPresent();
        verify(contactsRepository, times(1)).findById(1L);
        verify(contactsRepository, times(1)).deleteById(1L);
    }

}
