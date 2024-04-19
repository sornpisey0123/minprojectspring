package com.kid.minprojectspringg1btb.model.dto.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequest {
  @NotNull
  @NotBlank
  @NotEmpty
  @Email( regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
  private String email;

  @NotNull
  @NotBlank
  @NotEmpty
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$")
  private String password;

}
