package com.example.urlshortner.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
   private boolean isSuccessful;
   private String message;
}
