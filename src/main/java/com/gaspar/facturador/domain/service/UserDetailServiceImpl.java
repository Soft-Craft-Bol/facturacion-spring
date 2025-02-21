package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.AuthCreateUserRequest;
import com.gaspar.facturador.application.rest.dto.AuthLoginRequest;
import com.gaspar.facturador.application.rest.dto.AuthResponse;
import com.gaspar.facturador.persistence.crud.RoleRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.dto.UserDTO;
import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import com.gaspar.facturador.persistence.entity.UserEntity;
import com.gaspar.facturador.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
        );

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName()))
                );

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList
        );
    }


    public AuthResponse createUser(AuthCreateUserRequest createRoleRequest) {
        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        String email = createRoleRequest.email();
        Long telefono = Long.valueOf(createRoleRequest.telefono());
        List<String> rolesRequest = createRoleRequest.roleRequest().roleListName();

        // Convertir los nombres de roles a valores de RoleEnum
        List<RoleEnum> roleEnums = rolesRequest.stream()
                .map(role -> {
                    try {
                        return RoleEnum.valueOf(role.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid role name: " + role);
                    }
                })
                .collect(Collectors.toList());

        // Obtener los roles desde la base de datos
        Set<RoleEntity> roleEntityList = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(roleEnums));

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified do not exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .telefono(telefono)
                .firstName(createRoleRequest.nombre())
                .lastName(createRoleRequest.apellido())
                .photo(createRoleRequest.photo())
                .roles(roleEntityList)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userSaved.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);
        // incluir foto en la respuesta
        String photo = userSaved.getPhoto();

        return new AuthResponse(username, "User created successfully", accessToken, true, photo);
    }


    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        // incluir foto en la respuesta
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));
        String photo = userEntity.getPhoto();

        AuthResponse authResponse = new AuthResponse(username, "User loged succesfully", accessToken, true, photo);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setTelefono(user.getTelefono());
                    dto.setPhoto(user.getPhoto());
                    dto.setRoles(user.getRoles().stream()
                            .map(role -> role.getRoleEnum().name())
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userEntity.setUsername(userDTO.getUsername());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setTelefono(userDTO.getTelefono());
        userEntity.setPhoto(userDTO.getPhoto());
        userEntity.setRoles(userDTO.getRoles().stream()
                .map(role -> roleRepository.findByRoleEnum(RoleEnum.valueOf(role))
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
                .collect(Collectors.toSet()));

        UserEntity updatedUser = userRepository.save(userEntity);

        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(updatedUser.getId());
        updatedUserDTO.setUsername(updatedUser.getUsername());
        updatedUserDTO.setFirstName(updatedUser.getFirstName());
        updatedUserDTO.setLastName(updatedUser.getLastName());
        updatedUserDTO.setEmail(updatedUser.getEmail());
        updatedUserDTO.setTelefono(updatedUser.getTelefono());
        updatedUserDTO.setPhoto(updatedUser.getPhoto());
        updatedUserDTO.setRoles(updatedUser.getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.toSet()));

        return updatedUserDTO;
    }

    //Obtener los usuarios por su id
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setTelefono(userEntity.getTelefono());
        userDTO.setPhoto(userEntity.getPhoto());
        userDTO.setRoles(userEntity.getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.toSet()));

        return userDTO;
    }
    public List<UserDTO> getVendedorUsers() {
        List<UserEntity> users = userRepository.findByRolesRoleEnum(RoleEnum.VENDEDOR);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private UserDTO convertToDTO(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setTelefono(user.getTelefono());
        dto.setPhoto(user.getPhoto());
        dto.setRoles(user.getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.toSet()));
        return dto;
    }
}
