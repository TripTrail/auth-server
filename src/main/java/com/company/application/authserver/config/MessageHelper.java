package com.company.application.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelper {

  @Autowired
  private MessageSource messageSource;

  public String getMessage(String key){
    return messageSource.getMessage(key, new Object[]{}, Locale.getDefault());
  }

}
