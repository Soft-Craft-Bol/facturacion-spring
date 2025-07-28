package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.UpdateUserRequest;
import com.gaspar.facturador.application.rest.dto.AuthCreateUserRequest;
import com.gaspar.facturador.application.rest.dto.AuthLoginRequest;
import com.gaspar.facturador.application.rest.dto.AuthResponse;
import com.gaspar.facturador.persistence.crud.HorarioCrudRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.crud.RoleRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.dto.HorarioDTO;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;
import com.gaspar.facturador.persistence.dto.UserDTO;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private HorarioCrudRepository horarioCrudRepository;

    @Autowired
    private PuntoVentaCrudRepository puntoVentaRepository;

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

        List<RoleEnum> roleEnums = rolesRequest.stream()
                .map(role -> {
                    try {
                        return RoleEnum.valueOf(role.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid role name: " + role);
                    }
                })
                .collect(Collectors.toList());

        Set<RoleEntity> roleEntityList = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(roleEnums));

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified do not exist.");
        }

        // Modificación importante: Manejar puntosVenta nulos
        Set<PuntoVentaEntity> puntosVenta = createRoleRequest.puntosVenta() != null
                ? new HashSet<>(createRoleRequest.puntosVenta())
                : new HashSet<>();

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .telefono(telefono)
                .firstName(createRoleRequest.nombre())
                .lastName(createRoleRequest.apellido())
                .photo(createRoleRequest.photo())
                .roles(roleEntityList)
                .puntosVenta(puntosVenta)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        // Resto del método permanece igual...
        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userSaved.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);
        String photo = userSaved.getPhoto();

        return new AuthResponse( userSaved.getId(), username, "User created successfully", accessToken, true, photo, userSaved.getPuntosVenta());
    }


    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));
        String photo = userEntity.getPhoto();
        Set<PuntoVentaEntity> puntosVenta = userEntity.getPuntosVenta();

        return new AuthResponse( userEntity.getId(), username, "User logged in successfully", accessToken, true, photo, puntosVenta);
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

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Set<RoleEnum> desiredRoles = Set.of(
                RoleEnum.ADMIN,
                RoleEnum.PANADERO,
                RoleEnum.MAESTRO,
                RoleEnum.VENDEDOR
        );

        return userRepository.findByRolesIn(desiredRoles, pageable)
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
                });
    }
    public void deleteById(long id){
        userRepository.deleteById(id);
    }


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

        userDTO.setPuntosVenta(userEntity.getPuntosVenta().stream()
                .map(pv -> new PuntoVentaDTO(
                        pv.getId(),
                        pv.getNombre()
                ))
                .collect(Collectors.toSet()));

        List<HorarioEntity> horarios = horarioCrudRepository.findByIdPanadero(id);
        userDTO.setHorarios(horarios.stream()
                .map(h -> new HorarioDTO(
                        h.getId(),
                        h.getHoraEntrada(),
                        h.getHoraSalida(),
                        h.getFechaEntrada(),
                        h.getFechaSalida(),
                        h.getDias()
                ))
                .collect(Collectors.toSet()));

        return userDTO;
    }
    public List<UserDTO> getVendedorUsers() {
        List<UserEntity> users = userRepository.findByRolesRoleEnum(RoleEnum.VENDEDOR);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UpdateUserRequest updateRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Actualizar campos básicos
        if (updateRequest.getUsername() != null) {
            userEntity.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getFirstName() != null) {
            userEntity.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            userEntity.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getEmail() != null) {
            userEntity.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getTelefono() != null) {
            userEntity.setTelefono(updateRequest.getTelefono());
        }
        if (updateRequest.getPhoto() != null) {
            userEntity.setPhoto(updateRequest.getPhoto());
        }

        // Actualizar contraseña solo si se proporciona
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        // Actualizar roles si se proporcionan
        if (updateRequest.getRoles() != null && !updateRequest.getRoles().isEmpty()) {
            Set<RoleEntity> newRoles = updateRequest.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleEnum(RoleEnum.valueOf(roleName))
                            .orElseThrow(() -> new IllegalArgumentException("Rol no válido: " + roleName)))
                    .collect(Collectors.toSet());
            userEntity.setRoles(newRoles);
        }

        if (updateRequest.getPuntosVentaIds() != null) {
            Set<PuntoVentaEntity> puntosVenta = new HashSet<>();

            for (Long pvId : updateRequest.getPuntosVentaIds()) {
                puntoVentaRepository.findById(Math.toIntExact(pvId)).ifPresent(puntosVenta::add);
            }

            userEntity.setPuntosVenta(puntosVenta);
        }

        UserEntity updatedUser = userRepository.save(userEntity);
        return convertToDTO(updatedUser);
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

        dto.setPuntosVenta(user.getPuntosVenta().stream()
                .map(pv -> new PuntoVentaDTO(pv.getId(), pv.getNombre()))
                .collect(Collectors.toSet()));

        return dto;
    }



}
