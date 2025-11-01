package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad que representa un cliente en el sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cliente",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cliente_codigo", columnNames = "codigoCliente"),
                @UniqueConstraint(name = "uk_cliente_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_cliente_celular", columnNames = "celular")
        }
)
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre o razón social es obligatorio")
    @Size(max = 100, message = "El nombre o razón social no debe exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreRazonSocial;

    @NotNull(message = "El código del tipo de documento es obligatorio")
    @Min(value = 1, message = "El código del tipo de documento debe ser mayor que 0")
    @Column(nullable = false)
    private Integer codigoTipoDocumentoIdentidad;

    @NotNull(message = "El número de documento es obligatorio")
    @Digits(integer = 20, fraction = 0, message = "El número de documento debe ser un número válido sin decimales")
    @Column(nullable = false, unique = false)
    private Long numeroDocumento;

    @Size(max = 10, message = "El complemento no debe exceder 5 caracteres")
    @Column(length =5)
    private String complemento;

    @NotBlank(message = "El código de cliente es obligatorio")
    @Size(max = 20, message = "El código de cliente no debe exceder 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String codigoCliente;

    @Pattern(regexp = "\\d{8,15}", message = "El número de celular debe contener solo dígitos y tener entre 8 y 15 números")
    @Column(unique = true, length = 15)
    private String celular;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no debe exceder 50 caracteres")
    @Column(unique = true, length = 50)
    private String email;

    @Column(name = "permite_credito")
    private Boolean permiteCredito = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credito_cliente_id", referencedColumnName = "id")
    private CreditoClienteEntity credito;

}
