package com.codebykarthick.sample.flumtree.models.dto.signup;

import java.util.List;

public record SignupResponseDto(boolean hasErrors, List<String> errorsList) {}
