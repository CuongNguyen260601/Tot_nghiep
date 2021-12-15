package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Status_Enum;
import com.localbrand.dto.GenderDTO;
import com.localbrand.dto.request.AddressRequestDTO;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.response.UserResponseDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;
import com.localbrand.entity.Address;
import com.localbrand.entity.Cart;
import com.localbrand.entity.Gender;
import com.localbrand.entity.User;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.AddressRepository;
import com.localbrand.repository.CartRepository;
import com.localbrand.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@RequiredArgsConstructor
public class UserMapping implements Mapping<UserResponseDTO, User> {

    private final AddressRepository addressRepository;
    private final AddressMapping addressMapping;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;
    private final CartRepository cartRepository;

    @Override
    public UserResponseDTO toDto(User user) {
        Address address = this.addressRepository.findById(user.getIdAddress().longValue()).orElse(null);
        Gender gender = this.genderRepository.findById(user.getIdGender().longValue()).orElse(null);
        Cart cart = this.cartRepository.findFirstByIdUserAndIdStatus(user.getIdUser().intValue(), Status_Enum.EXISTS.getCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return UserResponseDTO
                .builder()
                .idUser(user.getIdUser())
                .idChat(user.getIdChat())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(sdf.format(user.getDateOfBirth()))
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .genderDTO(
                        GenderDTO.builder()
                                .idGender(gender.getIdGender())
                                .nameGender(gender.getNameGender())
                        .build())
                .addressDTO(AddressRequestDTO
                        .builder()
                        .idAddress(address.getIdAddress())
                        .idProvince(address.getIdProvince())
                        .idCommune(address.getIdCommune())
                        .idDistrict(address.getIdDistrict())
                        .detailAddress(address.getDetailAddress())
                        .build())
                .idStatus(user.getIdStatus())
                .imageUser(user.getImageUser())
                .idCart(cart.getIdCart().intValue())
                .build();
    }

    @Override
    public User toEntity(UserResponseDTO userResponseDTO) {
        return null;
    }

    public User toEntitySignUp (UserRequestDTO userRequestDTO){
        Address address = this.addressMapping.toEntitySave(userRequestDTO.getAddressRequestDTO());

        this.addressRepository.save(address);

        return User.builder()
                .idUser(userRequestDTO.getIdUser())
                .idChat(userRequestDTO.getIdChat())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .passwordUser(passwordEncoder.encode(userRequestDTO.getPasswordUser()))
                .idRole(userRequestDTO.getIdRole())
                .idGender(userRequestDTO.getIdGender())
                .idAddress(address.getIdAddress().intValue())
                .idStatus(userRequestDTO.getIdStatus())
                .imageUser(userRequestDTO.getImageUser())
                .build();
    }

    public UserResponseSignupDTO toDtoSignup(User user ,String access_token, String refresh_token) {
        Address address = this.addressRepository.findById(user.getIdAddress().longValue()).orElse(null);
        Gender gender = this.genderRepository.findById(user.getIdGender().longValue()).orElse(null);
        Cart cart = this.cartRepository.findFirstByIdUserAndIdStatus(user.getIdUser().intValue(), Status_Enum.EXISTS.getCode());
        UserResponseSignupDTO userResponseSignupDTO = new UserResponseSignupDTO();
        userResponseSignupDTO.setAccess_token(access_token);
        userResponseSignupDTO.setRefresh_token(refresh_token);
        userResponseSignupDTO.setIdUser(user.getIdUser());
        userResponseSignupDTO.setIdChat(user.getIdChat());
        userResponseSignupDTO.setFirstName(user.getFirstName());
        userResponseSignupDTO.setLastName(user.getLastName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        userResponseSignupDTO.setDateOfBirth(sdf.format(user.getDateOfBirth()));
        userResponseSignupDTO.setEmail(user.getEmail());
        userResponseSignupDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseSignupDTO.setGenderDTO(
                        GenderDTO.builder()
                                .idGender(gender.getIdGender())
                                .nameGender(gender.getNameGender())
                                .build());
        userResponseSignupDTO.setAddressDTO(AddressRequestDTO
                .builder()
                .idAddress(address.getIdAddress())
                .idProvince(address.getIdProvince())
                .idCommune(address.getIdCommune())
                .idDistrict(address.getIdDistrict())
                .detailAddress(address.getDetailAddress())
                .build());
        userResponseSignupDTO.setIdStatus(user.getIdStatus());
        userResponseSignupDTO.setImageUser(user.getImageUser());
        userResponseSignupDTO.setIdCart(cart.getIdCart().intValue());
        userResponseSignupDTO.setIdRole(user.getIdRole());
        return userResponseSignupDTO;
    }
}
