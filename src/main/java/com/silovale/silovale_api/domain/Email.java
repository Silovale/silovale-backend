package com.silovale.silovale_api.domain;


public record Email(String[] toUser,
                   String subject,
                   String message){


}
